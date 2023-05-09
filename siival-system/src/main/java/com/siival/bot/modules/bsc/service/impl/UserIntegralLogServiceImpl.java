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

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.UserIntegralLogVo;
import com.siival.bot.modules.bsc.domain.UserIntegralLog;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserIntegralLogRepository;
import com.siival.bot.modules.bsc.service.UserIntegralLogService;
import com.siival.bot.modules.bsc.service.dto.UserIntegralLogDto;
import com.siival.bot.modules.bsc.service.dto.UserIntegralLogQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserIntegralLogMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-02-21
**/
@Service
@RequiredArgsConstructor
public class UserIntegralLogServiceImpl implements UserIntegralLogService {

    private final UserIntegralLogRepository userIntegralLogRepository;
    private final UserIntegralLogMapper userIntegralLogMapper;

    @Override
    public Map<String,Object> queryAll(UserIntegralLogQueryCriteria criteria, Pageable pageable){
        Page<UserIntegralLog> page = userIntegralLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userIntegralLogMapper::toDto));
    }

    @Override
    public List<UserIntegralLogDto> queryAll(UserIntegralLogQueryCriteria criteria){
        return userIntegralLogMapper.toDto(userIntegralLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserIntegralLogDto findById(Integer id) {
        UserIntegralLog userIntegralLog = userIntegralLogRepository.findById(id).orElseGet(UserIntegralLog::new);
        ValidationUtil.isNull(userIntegralLog.getId(),"UserIntegralLog","id",id);
        return userIntegralLogMapper.toDto(userIntegralLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserIntegralLogDto create(UserIntegralLog resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return userIntegralLogMapper.toDto(userIntegralLogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserIntegralLog resources) {
        UserIntegralLog userIntegralLog = userIntegralLogRepository.findById(resources.getId()).orElseGet(UserIntegralLog::new);
        ValidationUtil.isNull( userIntegralLog.getId(),"UserIntegralLog","id",resources.getId());
        userIntegralLog.copy(resources);
        userIntegralLogRepository.save(userIntegralLog);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userIntegralLogRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserIntegralLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserIntegralLogDto userIntegralLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", userIntegralLog.getUser().getNickName());
            map.put("积分", userIntegralLog.getIntegral());
            map.put("类型", userIntegralLog.getType());
            map.put("操作前数量", userIntegralLog.getBeforeCount());
            map.put("操作后数量", userIntegralLog.getAfterCount());
            map.put("备注", userIntegralLog.getRemark());
            map.put("创建时间", userIntegralLog.getCreateTime());
            map.put("更新时间", userIntegralLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<UserIntegralLogVo> findIntegralLogByPage(BaseReq req) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"id") );
        Pageable pageable =   PageRequest.of(req.getPage(), req.getSize(), sort  );
        List<UserIntegralLog> list = userIntegralLogRepository.findByUserId(req.getUid(), pageable);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.stream().map(s->{
            UserIntegralLogVo vo = new UserIntegralLogVo();
            vo.setIntegral(s.getIntegral());
            vo.setBeforeCount(s.getBeforeCount());
            vo.setAfterCount(s.getAfterCount());
            vo.setRemark(s.getRemark());
            vo.setType(s.getType());
            String createTime = s.getCreateTime().toString();
            vo.setCreateTime(createTime.substring(0,createTime.indexOf(".")));
            return vo;
        }).collect(Collectors.toList());
    }
}