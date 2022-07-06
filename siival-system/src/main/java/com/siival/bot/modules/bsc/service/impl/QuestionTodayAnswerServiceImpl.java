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

import com.siival.bot.modules.bsc.domain.QuestionTodayAnswer;
import com.siival.bot.utils.FileUtil;
import com.siival.bot.utils.PageUtil;
import com.siival.bot.utils.QueryHelp;
import com.siival.bot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.QuestionTodayAnswerRepository;
import com.siival.bot.modules.bsc.service.QuestionTodayAnswerService;
import com.siival.bot.modules.bsc.service.dto.QuestionTodayAnswerDto;
import com.siival.bot.modules.bsc.service.dto.QuestionTodayAnswerQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.QuestionTodayAnswerMapper;
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
* @author Mark
* @date 2022-03-28
**/
@Service
@RequiredArgsConstructor
public class QuestionTodayAnswerServiceImpl implements QuestionTodayAnswerService {

    private final QuestionTodayAnswerRepository questionTodayAnswerRepository;
    private final QuestionTodayAnswerMapper questionTodayAnswerMapper;

    @Override
    public Map<String,Object> queryAll(QuestionTodayAnswerQueryCriteria criteria, Pageable pageable){
        Page<QuestionTodayAnswer> page = questionTodayAnswerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(questionTodayAnswerMapper::toDto));
    }

    @Override
    public List<QuestionTodayAnswerDto> queryAll(QuestionTodayAnswerQueryCriteria criteria){
        return questionTodayAnswerMapper.toDto(questionTodayAnswerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QuestionTodayAnswerDto findById(Integer id) {
        QuestionTodayAnswer questionTodayAnswer = questionTodayAnswerRepository.findById(id).orElseGet(QuestionTodayAnswer::new);
        ValidationUtil.isNull(questionTodayAnswer.getId(),"QuestionTodayAnswer","id",id);
        return questionTodayAnswerMapper.toDto(questionTodayAnswer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionTodayAnswerDto create(QuestionTodayAnswer resources) {
        return questionTodayAnswerMapper.toDto(questionTodayAnswerRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuestionTodayAnswer resources) {
        QuestionTodayAnswer questionTodayAnswer = questionTodayAnswerRepository.findById(resources.getId()).orElseGet(QuestionTodayAnswer::new);
        ValidationUtil.isNull( questionTodayAnswer.getId(),"QuestionTodayAnswer","id",resources.getId());
        questionTodayAnswer.copy(resources);
        questionTodayAnswerRepository.save(questionTodayAnswer);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            questionTodayAnswerRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QuestionTodayAnswerDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuestionTodayAnswerDto questionTodayAnswer : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题目", questionTodayAnswer.getQuestion().getQuestion());
            map.put("获得积分", questionTodayAnswer.getIntegral());
            map.put("用户", questionTodayAnswer.getUser().getNickName());
            map.put("用户答案", questionTodayAnswer.getUserAnswer());
            map.put("是否答对", questionTodayAnswer.getStatus());
            map.put("创建时间", questionTodayAnswer.getCreateTime());
            map.put("更新时间", questionTodayAnswer.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}