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
package com.siival.bot.modules.bsc.rest;

import com.siival.bot.annotation.Log;
import com.siival.bot.modules.bsc.domain.QuestionTodayAnswer;
import com.siival.bot.modules.bsc.service.QuestionTodayAnswerService;
import com.siival.bot.modules.bsc.service.dto.QuestionTodayAnswerQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @author Mark
* @date 2022-03-28
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "每日一题答题管理")
@RequestMapping("/bsc/questionTodayAnswer")
public class QuestionTodayAnswerController {

    private final QuestionTodayAnswerService questionTodayAnswerService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('questionTodayAnswer:list')")
    public void exportQuestionTodayAnswer(HttpServletResponse response, QuestionTodayAnswerQueryCriteria criteria) throws IOException {
        questionTodayAnswerService.download(questionTodayAnswerService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询每日一题答题")
    @ApiOperation("查询每日一题答题")
    @PreAuthorize("@el.check('questionTodayAnswer:list')")
    public ResponseEntity<Object> queryQuestionTodayAnswer(QuestionTodayAnswerQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(questionTodayAnswerService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增每日一题答题")
    @ApiOperation("新增每日一题答题")
    @PreAuthorize("@el.check('questionTodayAnswer:add')")
    public ResponseEntity<Object> createQuestionTodayAnswer(@Validated @RequestBody QuestionTodayAnswer resources){
        return new ResponseEntity<>(questionTodayAnswerService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改每日一题答题")
    @ApiOperation("修改每日一题答题")
    @PreAuthorize("@el.check('questionTodayAnswer:edit')")
    public ResponseEntity<Object> updateQuestionTodayAnswer(@Validated @RequestBody QuestionTodayAnswer resources){
        questionTodayAnswerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除每日一题答题")
    @ApiOperation("删除每日一题答题")
    @PreAuthorize("@el.check('questionTodayAnswer:del')")
    public ResponseEntity<Object> deleteQuestionTodayAnswer(@RequestBody Integer[] ids) {
        questionTodayAnswerService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}