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
import com.siival.bot.modules.bsc.domain.UserInfo;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.modules.bsc.service.dto.UserInfoQueryCriteria;
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
@Api(tags = "用户信息管理")
@RequestMapping("/bsc/userInfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userInfo:list')")
    public void exportUserInfo(HttpServletResponse response, UserInfoQueryCriteria criteria) throws IOException {
        userInfoService.download(userInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询用户信息")
    @ApiOperation("查询用户信息")
    @PreAuthorize("@el.check('userInfo:list')")
    public ResponseEntity<Object> queryUserInfo(UserInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增用户信息")
    @ApiOperation("新增用户信息")
    @PreAuthorize("@el.check('userInfo:add')")
    public ResponseEntity<Object> createUserInfo(@Validated @RequestBody UserInfo resources){
        return new ResponseEntity<>(userInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改用户信息")
    @ApiOperation("修改用户信息")
    @PreAuthorize("@el.check('userInfo:edit')")
    public ResponseEntity<Object> updateUserInfo(@Validated @RequestBody UserInfo resources){
        userInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除用户信息")
    @ApiOperation("删除用户信息")
    @PreAuthorize("@el.check('userInfo:del')")
    public ResponseEntity<Object> deleteUserInfo(@RequestBody Integer[] ids) {
        userInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}