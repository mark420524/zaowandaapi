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
import com.siival.bot.modules.bsc.domain.QuestionInfo;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.dto.QuestionInfoQueryCriteria;
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
* @author mark
* @date 2022-02-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "问题管理")
@RequestMapping("/bsc/questionInfo")
public class QuestionInfoController {

    private final QuestionInfoService questionInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('questionInfo:list')")
    public void exportQuestionInfo(HttpServletResponse response, QuestionInfoQueryCriteria criteria) throws IOException {
        questionInfoService.download(questionInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询问题")
    @ApiOperation("查询问题")
    @PreAuthorize("@el.check('questionInfo:list')")
    public ResponseEntity<Object> queryQuestionInfo(QuestionInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(questionInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增问题")
    @ApiOperation("新增问题")
    @PreAuthorize("@el.check('questionInfo:add')")
    public ResponseEntity<Object> createQuestionInfo(@Validated @RequestBody QuestionInfo resources){
        return new ResponseEntity<>(questionInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改问题")
    @ApiOperation("修改问题")
    @PreAuthorize("@el.check('questionInfo:edit')")
    public ResponseEntity<Object> updateQuestionInfo(@Validated @RequestBody QuestionInfo resources){
        questionInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除问题")
    @ApiOperation("删除问题")
    @PreAuthorize("@el.check('questionInfo:del')")
    public ResponseEntity<Object> deleteQuestionInfo(@RequestBody Integer[] ids) {
//        questionInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}