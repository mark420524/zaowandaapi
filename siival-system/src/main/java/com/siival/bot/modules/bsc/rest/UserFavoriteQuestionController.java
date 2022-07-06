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
import com.siival.bot.modules.bsc.domain.UserFavoriteQuestion;
import com.siival.bot.modules.bsc.service.UserFavoriteQuestionService;
import com.siival.bot.modules.bsc.service.dto.UserFavoriteQuestionQueryCriteria;
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
@Api(tags = "收藏题库管理")
@RequestMapping("/bsc/userFavoriteQuestion")
public class UserFavoriteQuestionController {

    private final UserFavoriteQuestionService userFavoriteQuestionService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userFavoriteQuestion:list')")
    public void exportUserFavoriteQuestion(HttpServletResponse response, UserFavoriteQuestionQueryCriteria criteria) throws IOException {
        userFavoriteQuestionService.download(userFavoriteQuestionService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询收藏题库")
    @ApiOperation("查询收藏题库")
    @PreAuthorize("@el.check('userFavoriteQuestion:list')")
    public ResponseEntity<Object> queryUserFavoriteQuestion(UserFavoriteQuestionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userFavoriteQuestionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增收藏题库")
    @ApiOperation("新增收藏题库")
    @PreAuthorize("@el.check('userFavoriteQuestion:add')")
    public ResponseEntity<Object> createUserFavoriteQuestion(@Validated @RequestBody UserFavoriteQuestion resources){
        return new ResponseEntity<>(userFavoriteQuestionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改收藏题库")
    @ApiOperation("修改收藏题库")
    @PreAuthorize("@el.check('userFavoriteQuestion:edit')")
    public ResponseEntity<Object> updateUserFavoriteQuestion(@Validated @RequestBody UserFavoriteQuestion resources){
        userFavoriteQuestionService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除收藏题库")
    @ApiOperation("删除收藏题库")
    @PreAuthorize("@el.check('userFavoriteQuestion:del')")
    public ResponseEntity<Object> deleteUserFavoriteQuestion(@RequestBody Integer[] ids) {
        userFavoriteQuestionService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}