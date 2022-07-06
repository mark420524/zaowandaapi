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
import com.siival.bot.modules.bsc.domain.UserCorrectQuestion;
import com.siival.bot.modules.bsc.service.UserCorrectQuestionService;
import com.siival.bot.modules.bsc.service.dto.UserCorrectQuestionQueryCriteria;
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
* @date 2022-02-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "纠错管理管理")
@RequestMapping("/bsc/userCorrectQuestion")
public class UserCorrectQuestionController {

    private final UserCorrectQuestionService userCorrectQuestionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userCorrectQuestion:list')")
    public void exportUserCorrectQuestion(HttpServletResponse response, UserCorrectQuestionQueryCriteria criteria) throws IOException {
        userCorrectQuestionService.download(userCorrectQuestionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询纠错管理")
    @ApiOperation("查询纠错管理")
    @PreAuthorize("@el.check('userCorrectQuestion:list')")
    public ResponseEntity<Object> queryUserCorrectQuestion(UserCorrectQuestionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userCorrectQuestionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增纠错管理")
    @ApiOperation("新增纠错管理")
    @PreAuthorize("@el.check('userCorrectQuestion:add')")
    public ResponseEntity<Object> createUserCorrectQuestion(@Validated @RequestBody UserCorrectQuestion resources){
        return new ResponseEntity<>(userCorrectQuestionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改纠错管理")
    @ApiOperation("修改纠错管理")
    @PreAuthorize("@el.check('userCorrectQuestion:edit')")
    public ResponseEntity<Object> updateUserCorrectQuestion(@Validated @RequestBody UserCorrectQuestion resources){
        userCorrectQuestionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除纠错管理")
    @ApiOperation("删除纠错管理")
    @PreAuthorize("@el.check('userCorrectQuestion:del')")
    public ResponseEntity<Object> deleteUserCorrectQuestion(@RequestBody Integer[] ids) {
        userCorrectQuestionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}