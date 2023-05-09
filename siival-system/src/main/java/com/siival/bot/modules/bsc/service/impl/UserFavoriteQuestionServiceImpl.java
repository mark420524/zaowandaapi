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

import com.siival.bot.modules.bsc.domain.UserFavoriteQuestion;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserFavoriteQuestionRepository;
import com.siival.bot.modules.bsc.service.UserFavoriteQuestionService;
import com.siival.bot.modules.bsc.service.dto.UserFavoriteQuestionDto;
import com.siival.bot.modules.bsc.service.dto.UserFavoriteQuestionQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserFavoriteQuestionMapper;
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
* @date 2022-02-12
**/
@Service
@RequiredArgsConstructor
public class UserFavoriteQuestionServiceImpl implements UserFavoriteQuestionService {

    private final UserFavoriteQuestionRepository userFavoriteQuestionRepository;
    private final UserFavoriteQuestionMapper userFavoriteQuestionMapper;

    @Override
    public Map<String,Object> queryAll(UserFavoriteQuestionQueryCriteria criteria, Pageable pageable){
        Page<UserFavoriteQuestion> page = userFavoriteQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userFavoriteQuestionMapper::toDto));
    }

    @Override
    public List<UserFavoriteQuestionDto> queryAll(UserFavoriteQuestionQueryCriteria criteria){
        return userFavoriteQuestionMapper.toDto(userFavoriteQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserFavoriteQuestionDto findById(Integer id) {
        UserFavoriteQuestion userFavoriteQuestion = userFavoriteQuestionRepository.findById(id).orElseGet(UserFavoriteQuestion::new);
        ValidationUtil.isNull(userFavoriteQuestion.getId(),"UserFavoriteQuestion","id",id);
        return userFavoriteQuestionMapper.toDto(userFavoriteQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserFavoriteQuestionDto create(UserFavoriteQuestion resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return userFavoriteQuestionMapper.toDto(userFavoriteQuestionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserFavoriteQuestion resources) {
        UserFavoriteQuestion userFavoriteQuestion = userFavoriteQuestionRepository.findById(resources.getId()).orElseGet(UserFavoriteQuestion::new);
        ValidationUtil.isNull( userFavoriteQuestion.getId(),"UserFavoriteQuestion","id",resources.getId());
        userFavoriteQuestion.copy(resources);
        userFavoriteQuestionRepository.save(userFavoriteQuestion);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userFavoriteQuestionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserFavoriteQuestionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserFavoriteQuestionDto userFavoriteQuestion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题库分类id", userFavoriteQuestion.getPid());
            map.put("问题id", userFavoriteQuestion.getQuestionId());
            map.put("用户id", userFavoriteQuestion.getUid());
            map.put("创建时间", userFavoriteQuestion.getCreateTime());
            map.put("更新时间", userFavoriteQuestion.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}