/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.siival.bot.modules.bsc.service.impl;

import com.siival.bot.modules.bsc.domain.ExamInfo;
import com.siival.bot.modules.bsc.domain.ExamTemp;
import com.siival.bot.modules.bsc.repository.ExamInfoRepository;
import com.siival.bot.util.UUIDUtil;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.ExamTempRepository;
import com.siival.bot.modules.bsc.service.ExamTempService;
import com.siival.bot.modules.bsc.service.dto.ExamTempDto;
import com.siival.bot.modules.bsc.service.dto.ExamTempQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.ExamTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website
* @description 服务实现
* @author Mark
* @date 2022-03-31
**/
@Service
@RequiredArgsConstructor
public class ExamTempServiceImpl implements ExamTempService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExamTempRepository examTempRepository;
    private final ExamTempMapper examTempMapper;

    private final ExamInfoRepository examInfoRepository;

    @Override
    public Map<String,Object> queryAll(ExamTempQueryCriteria criteria, Pageable pageable){
        Page<ExamTemp> page = examTempRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(examTempMapper::toDto));
    }

    @Override
    public List<ExamTempDto> queryAll(ExamTempQueryCriteria criteria){
        return examTempMapper.toDto(examTempRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ExamTempDto findById(Integer id) {
        ExamTemp examTemp = examTempRepository.findById(id).orElseGet(ExamTemp::new);
        ValidationUtil.isNull(examTemp.getId(),"ExamTemp","id",id);
        return examTempMapper.toDto(examTemp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExamTempDto create(ExamTemp resources) {
        return examTempMapper.toDto(examTempRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ExamTemp resources) {
        ExamTemp examTemp = examTempRepository.findById(resources.getId()).orElseGet(ExamTemp::new);
        ValidationUtil.isNull( examTemp.getId(),"ExamTemp","id",resources.getId());
        examTemp.copy(resources);
        examTempRepository.save(examTemp);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            examTempRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ExamTempDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExamTempDto examTemp : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" hrefHtml",  examTemp.getHrefHtml());
            map.put(" examName",  examTemp.getExamName());
            map.put("状态", examTemp.getStatus());
            map.put("创建时间", examTemp.getCreateTime());
            map.put("更新时间", examTemp.getUpdateTime());
            map.put("rar文件路径", examTemp.getFileUrl());
            map.put("程序处理文件路径", examTemp.getHandlerFileUrl());
            map.put("教材版本version", examTemp.getVersion());
            map.put("pid", examTemp.getPid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerExamTemp() {
        logger.info("开始处理临时exam");
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id") );
        int page = 0;
        int size = 100;
        Integer status = 1;
        Pageable pageable =   PageRequest.of(page, size, sort  );

        Page<ExamTemp> pageResult = examTempRepository.findByStatus(status, pageable);
        if (pageResult.isEmpty()) {
            logger.info("临时exam为空");
            return ;
        }
        logger.info("临时exam数量为:{}",pageResult.getTotalElements());

        List<ExamTemp> list = pageResult.getContent();
        for (ExamTemp temp:list) {
            ExamInfo info = buildExamInfo(temp);
            examInfoRepository.save(info);
            temp.setStatus(3);
            temp.setUpdateTime(TimeUtils.getCurrentTimestamp());
            examTempRepository.save(temp);
        }
    }

    private ExamInfo buildExamInfo(ExamTemp temp) {

        ExamInfo info = new ExamInfo();
        //System.out.println("试卷名称"+temp.getExamName());
        info.setExamName(temp.getExamName());
        info.setStatus(1);
        String fileLabel = UUIDUtil.getRandomUUID();
        info.setCreateTime(TimeUtils.getCurrentTimestamp());
        info.setFileLabel(  fileLabel);
        info.setTempId(temp.getId());
        info.setPid(temp.getPid());
        info.setVersion(temp.getVersion());
        info.setFileSize(buildFileSize(temp.getId(),fileLabel,info));
        return info;
    }

    private String buildFileSize(Integer id, String fileLabel,ExamInfo info ) {
        String folder = "E:\\tiku\\examtemp\\" + id;
        String output = "E:\\tiku\\output\\" + info.getPid()+"\\";
        File readFileFolder = new File(folder);
        String size = "0";
        try   {
            File[] files = readFileFolder.listFiles();
            for (File current: files) {
                String currentName = current.getName();
                if (currentName.endsWith(".doc") || currentName.endsWith(".docx")
                    || currentName.endsWith(".pdf")
                    || currentName.endsWith(".DOC") || currentName.endsWith(".DOCX")
                        || currentName.endsWith(".PDF")
                ) {
                    //System.out.println(current.getName());
                    long length = current.length();
                    size = getDataSize(length);
                    String ext = currentName.substring(currentName.lastIndexOf("."));
                    info.setFileType(ext);
                    String outputFilePath = output + id+"-"+ fileLabel+  ext;

                    File outputFile = new File(outputFilePath);
                    File parentFile = outputFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    FileUtil.copy(current, outputFile, true);
                    //System.out.println( length + "格式化后:" + size);
                    break;
                }

            }
//            FileUtil.copy()
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.0");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }
    }
}