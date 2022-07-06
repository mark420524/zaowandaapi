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
import com.siival.bot.modules.bsc.domain.ExamInfo;
import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.dto.ExamInfoQueryCriteria;
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
* @date 2022-03-31
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "试卷管理管理")
@RequestMapping("/bsc/examInfo")
public class ExamInfoController {

    private final ExamInfoService examInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('examInfo:list')")
    public void exportExamInfo(HttpServletResponse response, ExamInfoQueryCriteria criteria) throws IOException {
        examInfoService.download(examInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询试卷管理")
    @ApiOperation("查询试卷管理")
    @PreAuthorize("@el.check('examInfo:list')")
    public ResponseEntity<Object> queryExamInfo(ExamInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(examInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增试卷管理")
    @ApiOperation("新增试卷管理")
    @PreAuthorize("@el.check('examInfo:add')")
    public ResponseEntity<Object> createExamInfo(@Validated @RequestBody ExamInfo resources){
        return new ResponseEntity<>(examInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改试卷管理")
    @ApiOperation("修改试卷管理")
    @PreAuthorize("@el.check('examInfo:edit')")
    public ResponseEntity<Object> updateExamInfo(@Validated @RequestBody ExamInfo resources){
        examInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除试卷管理")
    @ApiOperation("删除试卷管理")
    @PreAuthorize("@el.check('examInfo:del')")
    public ResponseEntity<Object> deleteExamInfo(@RequestBody Integer[] ids) {
        examInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}