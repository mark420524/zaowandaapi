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
import com.siival.bot.modules.bsc.domain.UserExamScore;
import com.siival.bot.modules.bsc.service.UserExamScoreService;
import com.siival.bot.modules.bsc.service.dto.UserExamScoreQueryCriteria;
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
* @date 2022-02-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "得分记录管理")
@RequestMapping("/bsc/userExamScore")
public class UserExamScoreController {

    private final UserExamScoreService userExamScoreService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userExamScore:list')")
    public void exportUserExamScore(HttpServletResponse response, UserExamScoreQueryCriteria criteria) throws IOException {
        userExamScoreService.download(userExamScoreService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询得分记录")
    @ApiOperation("查询得分记录")
    @PreAuthorize("@el.check('userExamScore:list')")
    public ResponseEntity<Object> queryUserExamScore(UserExamScoreQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userExamScoreService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增得分记录")
    @ApiOperation("新增得分记录")
    @PreAuthorize("@el.check('userExamScore:add')")
    public ResponseEntity<Object> createUserExamScore(@Validated @RequestBody UserExamScore resources){
        return new ResponseEntity<>(userExamScoreService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改得分记录")
    @ApiOperation("修改得分记录")
    @PreAuthorize("@el.check('userExamScore:edit')")
    public ResponseEntity<Object> updateUserExamScore(@Validated @RequestBody UserExamScore resources){
        userExamScoreService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除得分记录")
    @ApiOperation("删除得分记录")
    @PreAuthorize("@el.check('userExamScore:del')")
    public ResponseEntity<Object> deleteUserExamScore(@RequestBody Integer[] ids) {
        userExamScoreService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}