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
import com.siival.bot.modules.bsc.domain.UserWrongQuestion;
import com.siival.bot.modules.bsc.service.UserWrongQuestionService;
import com.siival.bot.modules.bsc.service.dto.UserWrongQuestionQueryCriteria;
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
@Api(tags = "错题集合管理")
@RequestMapping("/bsc/userWrongQuestion")
public class UserWrongQuestionController {

    private final UserWrongQuestionService userWrongQuestionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userWrongQuestion:list')")
    public void exportUserWrongQuestion(HttpServletResponse response, UserWrongQuestionQueryCriteria criteria) throws IOException {
        userWrongQuestionService.download(userWrongQuestionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询错题集合")
    @ApiOperation("查询错题集合")
    @PreAuthorize("@el.check('userWrongQuestion:list')")
    public ResponseEntity<Object> queryUserWrongQuestion(UserWrongQuestionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userWrongQuestionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增错题集合")
    @ApiOperation("新增错题集合")
    @PreAuthorize("@el.check('userWrongQuestion:add')")
    public ResponseEntity<Object> createUserWrongQuestion(@Validated @RequestBody UserWrongQuestion resources){
        return new ResponseEntity<>(userWrongQuestionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改错题集合")
    @ApiOperation("修改错题集合")
    @PreAuthorize("@el.check('userWrongQuestion:edit')")
    public ResponseEntity<Object> updateUserWrongQuestion(@Validated @RequestBody UserWrongQuestion resources){
        userWrongQuestionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除错题集合")
    @ApiOperation("删除错题集合")
    @PreAuthorize("@el.check('userWrongQuestion:del')")
    public ResponseEntity<Object> deleteUserWrongQuestion(@RequestBody Integer[] ids) {
        userWrongQuestionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}