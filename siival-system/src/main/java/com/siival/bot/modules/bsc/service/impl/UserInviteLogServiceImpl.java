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
import com.siival.bot.modules.api.resp.UserInviteLogVo;
import com.siival.bot.modules.bsc.domain.UserInviteLog;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserInviteLogRepository;
import com.siival.bot.modules.bsc.service.UserInviteLogService;
import com.siival.bot.modules.bsc.service.dto.UserInviteLogDto;
import com.siival.bot.modules.bsc.service.dto.UserInviteLogQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserInviteLogMapper;
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
* @author Mark
* @date 2022-03-29
**/
@Service
@RequiredArgsConstructor
public class UserInviteLogServiceImpl implements UserInviteLogService {

    private final UserInviteLogRepository userInviteLogRepository;
    private final UserInviteLogMapper userInviteLogMapper;

    @Override
    public Map<String,Object> queryAll(UserInviteLogQueryCriteria criteria, Pageable pageable){
        Page<UserInviteLog> page = userInviteLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userInviteLogMapper::toDto));
    }

    @Override
    public List<UserInviteLogDto> queryAll(UserInviteLogQueryCriteria criteria){
        return userInviteLogMapper.toDto(userInviteLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserInviteLogDto findById(Integer id) {
        UserInviteLog userInviteLog = userInviteLogRepository.findById(id).orElseGet(UserInviteLog::new);
        ValidationUtil.isNull(userInviteLog.getId(),"UserInviteLog","id",id);
        return userInviteLogMapper.toDto(userInviteLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInviteLogDto create(UserInviteLog resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return userInviteLogMapper.toDto(userInviteLogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInviteLog resources) {
        UserInviteLog userInviteLog = userInviteLogRepository.findById(resources.getId()).orElseGet(UserInviteLog::new);
        ValidationUtil.isNull( userInviteLog.getId(),"UserInviteLog","id",resources.getId());
        userInviteLog.copy(resources);
        userInviteLogRepository.save(userInviteLog);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userInviteLogRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserInviteLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserInviteLogDto userInviteLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户", userInviteLog.getUser().getNickName() );
            map.put("获得积分", userInviteLog.getIntegral());
            map.put("邀请用户id", userInviteLog.getInviteUser().getNickName());
            map.put("创建时间", userInviteLog.getCreateTime());
            map.put("更新时间", userInviteLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    public List<UserInviteLogVo> findUserInviteLogVoByReq(BaseReq req) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"id") );
        Pageable pageable =   PageRequest.of(req.getPage(), req.getSize(), sort  );
        List<UserInviteLog> list = userInviteLogRepository.findByUserId(req.getUid(),pageable);
        if (list==null || list.isEmpty()) {
            return null;
        }
        return list.stream().map(s->{
            UserInviteLogVo vo = new UserInviteLogVo();
            vo.setIntegral(s.getIntegral());
            String createTime = s.getCreateTime().toString();
            vo.setCreateTime(createTime.substring(0,createTime.indexOf(".")));
            String nickName = s.getInviteUser().getNickName();
            if (StringUtils.isNotBlank(nickName)) {
                vo.setNickName(nickName.length()>10?nickName.substring(0,7)+"...":nickName);
            }else{
                vo.setNickName("微信用户");
            }
            return vo;
        }).collect(Collectors.toList());
    }
}