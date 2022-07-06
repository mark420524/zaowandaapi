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
import com.siival.bot.modules.bsc.domain.UserInviteLog;
import com.siival.bot.modules.bsc.service.UserInviteLogService;
import com.siival.bot.modules.bsc.service.dto.UserInviteLogQueryCriteria;
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
* @date 2022-03-29
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "邀请记录管理")
@RequestMapping("/bsc/userInviteLog")
public class UserInviteLogController {

    private final UserInviteLogService userInviteLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userInviteLog:list')")
    public void exportUserInviteLog(HttpServletResponse response, UserInviteLogQueryCriteria criteria) throws IOException {
        userInviteLogService.download(userInviteLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询邀请记录")
    @ApiOperation("查询邀请记录")
    @PreAuthorize("@el.check('userInviteLog:list')")
    public ResponseEntity<Object> queryUserInviteLog(UserInviteLogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userInviteLogService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增邀请记录")
    @ApiOperation("新增邀请记录")
    @PreAuthorize("@el.check('userInviteLog:add')")
    public ResponseEntity<Object> createUserInviteLog(@Validated @RequestBody UserInviteLog resources){
        return new ResponseEntity<>(userInviteLogService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改邀请记录")
    @ApiOperation("修改邀请记录")
    @PreAuthorize("@el.check('userInviteLog:edit')")
    public ResponseEntity<Object> updateUserInviteLog(@Validated @RequestBody UserInviteLog resources){
        userInviteLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除邀请记录")
    @ApiOperation("删除邀请记录")
    @PreAuthorize("@el.check('userInviteLog:del')")
    public ResponseEntity<Object> deleteUserInviteLog(@RequestBody Integer[] ids) {
        //userInviteLogService.deleteAll(ids);
        //DO NOTHING
        return new ResponseEntity<>(HttpStatus.OK);
    }
}