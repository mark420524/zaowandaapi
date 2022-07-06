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
import com.siival.bot.modules.bsc.domain.ExamTemp;
import com.siival.bot.modules.bsc.service.ExamTempService;
import com.siival.bot.modules.bsc.service.dto.ExamTempQueryCriteria;
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
* @date 2022-03-31
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "临时试卷管理")
@RequestMapping("/bsc/examTemp")
public class ExamTempController {

    private final ExamTempService examTempService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('examTemp:list')")
    public void exportExamTemp(HttpServletResponse response, ExamTempQueryCriteria criteria) throws IOException {
        examTempService.download(examTempService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询临时试卷")
    @ApiOperation("查询临时试卷")
    @PreAuthorize("@el.check('examTemp:list')")
    public ResponseEntity<Object> queryExamTemp(ExamTempQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(examTempService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增临时试卷")
    @ApiOperation("新增临时试卷")
    @PreAuthorize("@el.check('examTemp:add')")
    public ResponseEntity<Object> createExamTemp(@Validated @RequestBody ExamTemp resources){
        return new ResponseEntity<>(examTempService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改临时试卷")
    @ApiOperation("修改临时试卷")
    @PreAuthorize("@el.check('examTemp:edit')")
    public ResponseEntity<Object> updateExamTemp(@Validated @RequestBody ExamTemp resources){
        examTempService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除临时试卷")
    @ApiOperation("删除临时试卷")
    @PreAuthorize("@el.check('examTemp:del')")
    public ResponseEntity<Object> deleteExamTemp(@RequestBody Integer[] ids) {
        examTempService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}