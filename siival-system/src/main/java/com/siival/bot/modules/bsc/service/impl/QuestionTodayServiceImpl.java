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

import com.siival.bot.modules.bsc.domain.QuestionToday;
import com.siival.bot.utils.FileUtil;
import com.siival.bot.utils.PageUtil;
import com.siival.bot.utils.QueryHelp;
import com.siival.bot.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.QuestionTodayRepository;
import com.siival.bot.modules.bsc.service.QuestionTodayService;
import com.siival.bot.modules.bsc.service.dto.QuestionTodayDto;
import com.siival.bot.modules.bsc.service.dto.QuestionTodayQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.QuestionTodayMapper;
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
public class QuestionTodayServiceImpl implements QuestionTodayService {

    private final QuestionTodayRepository questionTodayRepository;
    private final QuestionTodayMapper questionTodayMapper;

    @Override
    public Map<String,Object> queryAll(QuestionTodayQueryCriteria criteria, Pageable pageable){
        Page<QuestionToday> page = questionTodayRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(questionTodayMapper::toDto));
    }

    @Override
    public List<QuestionTodayDto> queryAll(QuestionTodayQueryCriteria criteria){
        return questionTodayMapper.toDto(questionTodayRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QuestionTodayDto findById(Integer id) {
        QuestionToday questionToday = questionTodayRepository.findById(id).orElseGet(QuestionToday::new);
        ValidationUtil.isNull(questionToday.getId(),"QuestionToday","id",id);
        return questionTodayMapper.toDto(questionToday);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionTodayDto create(QuestionToday resources) {
        return questionTodayMapper.toDto(questionTodayRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuestionToday resources) {
        QuestionToday questionToday = questionTodayRepository.findById(resources.getId()).orElseGet(QuestionToday::new);
        ValidationUtil.isNull( questionToday.getId(),"QuestionToday","id",resources.getId());
        questionToday.copy(resources);
        questionTodayRepository.save(questionToday);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            questionTodayRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QuestionTodayDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuestionTodayDto questionToday : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题目", questionToday.getQuestion().getQuestion());
            map.put("答对获得积分", questionToday.getIntegral());
            map.put("是否启用", questionToday.getStatus());
            map.put("创建时间", questionToday.getCreateTime());
            map.put("更新时间", questionToday.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}