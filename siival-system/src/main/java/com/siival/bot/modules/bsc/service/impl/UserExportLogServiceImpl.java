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
import com.siival.bot.modules.bsc.domain.UserExportLog;
import com.siival.bot.service.EmailService;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserExportLogRepository;
import com.siival.bot.modules.bsc.service.UserExportLogService;
import com.siival.bot.modules.bsc.service.dto.UserExportLogDto;
import com.siival.bot.modules.bsc.service.dto.UserExportLogQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserExportLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-03-11
**/
@Service
@RequiredArgsConstructor
public class UserExportLogServiceImpl implements UserExportLogService {

    private final UserExportLogRepository userExportLogRepository;
    private final UserExportLogMapper userExportLogMapper;
    private final FileProperties fileProperties;
    private final EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(UserExportLogServiceImpl.class);

    @Override
    public Map<String,Object> queryAll(UserExportLogQueryCriteria criteria, Pageable pageable){
        Page<UserExportLog> page = userExportLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userExportLogMapper::toDto));
    }

    @Override
    public List<UserExportLogDto> queryAll(UserExportLogQueryCriteria criteria){
        return userExportLogMapper.toDto(userExportLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserExportLogDto findById(Integer id) {
        UserExportLog userExportLog = userExportLogRepository.findById(id).orElseGet(UserExportLog::new);
        ValidationUtil.isNull(userExportLog.getId(),"UserExportLog","id",id);
        return userExportLogMapper.toDto(userExportLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserExportLogDto create(UserExportLog resources) {
        return userExportLogMapper.toDto(userExportLogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserExportLog resources) {
        UserExportLog userExportLog = userExportLogRepository.findById(resources.getId()).orElseGet(UserExportLog::new);
        ValidationUtil.isNull( userExportLog.getId(),"UserExportLog","id",resources.getId());
        userExportLog.copy(resources);
        userExportLogRepository.save(userExportLog);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userExportLogRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserExportLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserExportLogDto userExportLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", userExportLog.getUid());
            map.put("消耗积分", userExportLog.getIntegral());
            map.put("题库id", userExportLog.getPid());
            map.put("邮箱", userExportLog.getEmail());
            map.put("状态", userExportLog.getStatus());
            map.put("创建时间", userExportLog.getCreateTime());
            map.put("更新时间", userExportLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void saveUserExportLog(Integer uid, Integer type, Integer integral, Integer status, Integer examId, Integer pid, String filePath, String email) {
        UserExportLog log = new UserExportLog();
        log.setUid(uid);
        log.setExportType(type);
        log.setIntegral(integral);
        log.setStatus(status);
        log.setExamId(examId);
        log.setPid(pid);
        log.setFilePath(filePath);
        log.setCreateTime(TimeUtils.getCurrentTimestamp());
        log.setEmail(email);
        userExportLogRepository.save(log);
    }

    @Override
    public void saveUserPdfExportLog(Integer uid, Integer integral, String filePath, String email) {
        saveUserExportLog(uid, ExportTypeEnum.PDF.getCode(),integral,
                ExportStatusEnum.WAITING.getStatus(), null,null,filePath,email);
    }

    @Override
    public void exportPdf() {
        logger.info("开始处理发送pdf的定时任务");
        List<UserExportLog> list = userExportLogRepository.findByStatusAndExportType(ExportStatusEnum.WAITING.getStatus(), ExportTypeEnum.PDF.getCode());
        if (list==null || list.isEmpty()) {
            return;
        }
        for (UserExportLog log : list) {
            logger.info("开始处理发送pdf为：{}",log);
            String outputFile =  fileProperties.getPath().getPath() + log.getFilePath();
            EmailVo vo = new EmailVo();
            vo.setSubject("处理pdf文件");
            vo.setContent("pdf文件见附件") ;
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