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

import com.siival.bot.modules.bsc.domain.UserWrongQuestion;
import com.siival.bot.utils.FileUtil;
import com.siival.bot.utils.PageUtil;
import com.siival.bot.utils.QueryHelp;
import com.siival.bot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserWrongQuestionRepository;
import com.siival.bot.modules.bsc.service.UserWrongQuestionService;
import com.siival.bot.modules.bsc.service.dto.UserWrongQuestionDto;
import com.siival.bot.modules.bsc.service.dto.UserWrongQuestionQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserWrongQuestionMapper;
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
public class UserWrongQuestionServiceImpl implements UserWrongQuestionService {

    private final UserWrongQuestionRepository userWrongQuestionRepository;
    private final UserWrongQuestionMapper userWrongQuestionMapper;

    @Override
    public Map<String,Object> queryAll(UserWrongQuestionQueryCriteria criteria, Pageable pageable){
        Page<UserWrongQuestion> page = userWrongQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userWrongQuestionMapper::toDto));
    }

    @Override
    public List<UserWrongQuestionDto> queryAll(UserWrongQuestionQueryCriteria criteria){
        return userWrongQuestionMapper.toDto(userWrongQuestionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserWrongQuestionDto findById(Integer id) {
        UserWrongQuestion userWrongQuestion = userWrongQuestionRepository.findById(id).orElseGet(UserWrongQuestion::new);
        ValidationUtil.isNull(userWrongQuestion.getId(),"UserWrongQuestion","id",id);
        return userWrongQuestionMapper.toDto(userWrongQuestion);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserWrongQuestionDto create(UserWrongQuestion resources) {
        return userWrongQuestionMapper.toDto(userWrongQuestionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserWrongQuestion resources) {
        UserWrongQuestion userWrongQuestion = userWrongQuestionRepository.findById(resources.getId()).orElseGet(UserWrongQuestion::new);
        ValidationUtil.isNull( userWrongQuestion.getId(),"UserWrongQuestion","id",resources.getId());
        userWrongQuestion.copy(resources);
        userWrongQuestionRepository.save(userWrongQuestion);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userWrongQuestionRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserWrongQuestionDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserWrongQuestionDto userWrongQuestion : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题库分类id", userWrongQuestion.getPid());
            map.put("问题id", userWrongQuestion.getQuestionId());
            map.put("用户id", userWrongQuestion.getUid());
            map.put("创建时间", userWrongQuestion.getCreateTime());
            map.put("更新时间", userWrongQuestion.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}