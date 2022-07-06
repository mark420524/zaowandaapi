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

import com.siival.bot.config.FileProperties;
import com.siival.bot.domain.vo.EmailVo;
import com.siival.bot.enums.ExportStatusEnum;
import com.siival.bot.enums.ExportTypeEnum;
import com.siival.bot.enums.IntegralTypeEnum;
import com.siival.bot.modules.api.req.ExportInfoReq;
import com.siival.bot.modules.api.req.SearchExamReq;
import com.siival.bot.modules.api.resp.ExamExportInfo;
import com.siival.bot.modules.api.resp.ExamInfoVo;
import com.siival.bot.modules.bsc.constant.SystemSettingConstant;
import com.siival.bot.modules.bsc.domain.ExamInfo;
import com.siival.bot.modules.bsc.domain.UserExportLog;
import com.siival.bot.modules.bsc.repository.UserExportLogRepository;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.resp.PageResult;
import com.siival.bot.service.EmailService;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.ExamInfoRepository;
import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.dto.ExamInfoDto;
import com.siival.bot.modules.bsc.service.dto.ExamInfoQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.ExamInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-03-31
**/
@Service
@RequiredArgsConstructor
public class ExamInfoServiceImpl implements ExamInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ExamInfoRepository examInfoRepository;
    private final ExamInfoMapper examInfoMapper;
    private final UserExportLogRepository userExportLogRepository;
    private final UserInfoService userInfoService;
    private final EmailService emailService;
    private final FileProperties fileProperties;
    private final SysSettingService sysSettingService;

    @Override
    public Map<String,Object> queryAll(ExamInfoQueryCriteria criteria, Pageable pageable){
        Page<ExamInfo> page = examInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(examInfoMapper::toDto));
    }

    @Override
    public List<ExamInfoDto> queryAll(ExamInfoQueryCriteria criteria){
        return examInfoMapper.toDto(examInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ExamInfoDto findById(Integer id) {
        ExamInfo examInfo = examInfoRepository.findById(id).orElseGet(ExamInfo::new);
        ValidationUtil.isNull(examInfo.getId(),"ExamInfo","id",id);
        return examInfoMapper.toDto(examInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExamInfoDto create(ExamInfo resources) {
        return examInfoMapper.toDto(examInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ExamInfo resources) {
        ExamInfo examInfo = examInfoRepository.findById(resources.getId()).orElseGet(ExamInfo::new);
        ValidationUtil.isNull( examInfo.getId(),"ExamInfo","id",resources.getId());
        examInfo.copy(resources);
        examInfoRepository.save(examInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            examInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ExamInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExamInfoDto examInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" examName",  examInfo.getExamName());
            map.put("状态", examInfo.getStatus());
            map.put("创建时间", examInfo.getCreateTime());
            map.put("更新时间", examInfo.getUpdateTime());
            map.put("filelabel", examInfo.getFileLabel());
            map.put("file 大小", examInfo.getFileSize());
            map.put("教材版本", examInfo.getVersion());
            map.put("pid", examInfo.getPid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public PageResult<ExamInfoVo> findExamInfoVoByKeywords(SearchExamReq req) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id") );
        Pageable pageable =   PageRequest.of(req.getPage(), req.getSize(), sort  );

        PageResult<ExamInfoVo> result = new PageResult<>(req.getPage(), req.getSize());

        Page<ExamInfo> page = examInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,req,criteriaBuilder),pageable);
        if (page==null || page.isEmpty()) {
            result.setTotalSize(0L);
            result.setTotalPages(0L);
        }else {
            result.setTotalSize(page.getTotalElements());
            result.setTotalPages(page.getTotalPages());
            List<ExamInfo> list = page.getContent();
            result.setList(
                    list.stream().map(s->{
                ExamInfoVo vo = new ExamInfoVo();
                vo.setId(s.getId());
                vo.setFileSize(s.getFileSize());
                vo.setVersion(s.getVersion());
                vo.setFileType(s.getFileType());
                vo.setExamName(s.getExamName());
                return vo;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer exportExamInfo(ExportInfoReq req, Integer needIntegral) {
        UserExportLog log = new UserExportLog();
        log.setUid(req.getUid());
        log.setCreateTime(TimeUtils.getCurrentTimestamp());
        log.setEmail(req.getEmail());
        log.setExportType(ExportTypeEnum.EXAMS.getCode());
        log.setIntegral(needIntegral);
        log.setExamId(req.getEid());
        log.setStatus(ExportStatusEnum.WAITING.getStatus());
        userExportLogRepository.save(log);
        ModifyUserIntegralReq modifyReq = new ModifyUserIntegralReq();
        modifyReq.setId(req.getUid());
        modifyReq.setRemark("下载试卷");
        modifyReq.setIntegral(needIntegral);
        modifyReq.setStatus(IntegralTypeEnum.REDUCE_INTEGRAL.getType());
        userInfoService.saveUserIntegralLog(modifyReq);
        return 0;
    }

    @Override
    public void handlerExportExam() {
        logger.info("开始处理导出试卷的定时任务");
        List<UserExportLog> list = userExportLogRepository.findByStatusAndExportType(ExportStatusEnum.WAITING.getStatus(), ExportTypeEnum.EXAMS.getCode());
        if (list==null || list.isEmpty()) {
            return;
        }

        for (UserExportLog log : list) {
            logger.info("开始处理导出exam log为：{}",log);
            String outputFile = buildOutputFile(log.getExamId());
            if (outputFile!=null) {
                EmailVo vo = new EmailVo();
                vo.setSubject("下载试卷");
                vo.setContent("下载内容见附件") ;
                List<String> tos = new ArrayList<>();
                tos.add(log.getEmail());
                vo.setTos(tos);
                vo.setFileName(outputFile);
                try {
                    emailService.send(vo);
                    log.setStatus(ExportStatusEnum.SUCCESS.getStatus());
                    userExportLogRepository.save(log);
                } catch ( Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public ExamExportInfo findExamExportInfo(Integer uid) {
        Integer needIntegral = sysSettingService.findSystemSettingValueToInteger(SystemSettingConstant.EXAM_INTEGRAL);
        Integer integral = userInfoService.findUserIntegral(uid );
        ExamExportInfo info = new ExamExportInfo();
        info.setNeedIntegral(needIntegral);
        info.setIntegral(integral);
        return info;
    }

    private String buildOutputFile(Integer id) {
        ExamInfo info = examInfoRepository.findById(id).orElseGet(ExamInfo::new);
        if (StringUtils.isBlank(info.getFileLabel())) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(fileProperties.getPath().getPath());
        sb.append("exam").append(File.separator);
        sb.append(info.getTempId()).append("-").append(info.getFileLabel());
        sb.append(info.getFileType());
        return sb.toString();
    }
}