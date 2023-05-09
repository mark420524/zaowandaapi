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

import com.siival.bot.modules.bsc.domain.UserSignLog;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserSignLogRepository;
import com.siival.bot.modules.bsc.service.UserSignLogService;
import com.siival.bot.modules.bsc.service.dto.UserSignLogDto;
import com.siival.bot.modules.bsc.service.dto.UserSignLogQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserSignLogMapper;
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
* @date 2022-02-21
**/
@Service
@RequiredArgsConstructor
public class UserSignLogServiceImpl implements UserSignLogService {

    private final UserSignLogRepository userSignLogRepository;
    private final UserSignLogMapper userSignLogMapper;

    @Override
    public Map<String,Object> queryAll(UserSignLogQueryCriteria criteria, Pageable pageable){
        Page<UserSignLog> page = userSignLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userSignLogMapper::toDto));
    }

    @Override
    public List<UserSignLogDto> queryAll(UserSignLogQueryCriteria criteria){
        return userSignLogMapper.toDto(userSignLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserSignLogDto findById(Integer id) {
        UserSignLog userSignLog = userSignLogRepository.findById(id).orElseGet(UserSignLog::new);
        ValidationUtil.isNull(userSignLog.getId(),"UserSignLog","id",id);
        return userSignLogMapper.toDto(userSignLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSignLogDto create(UserSignLog resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return userSignLogMapper.toDto(userSignLogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserSignLog resources) {
        UserSignLog userSignLog = userSignLogRepository.findById(resources.getId()).orElseGet(UserSignLog::new);
        ValidationUtil.isNull( userSignLog.getId(),"UserSignLog","id",resources.getId());
        userSignLog.copy(resources);
        userSignLogRepository.save(userSignLog);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userSignLogRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserSignLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserSignLogDto userSignLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户昵称", userSignLog.getUser().getNickName());
            map.put("获得积分", userSignLog.getIntegral());
            map.put("创建时间", userSignLog.getCreateTime());
            map.put("更新时间", userSignLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}