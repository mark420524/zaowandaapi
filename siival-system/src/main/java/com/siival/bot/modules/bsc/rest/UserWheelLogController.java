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
import com.siival.bot.modules.bsc.domain.UserWheelLog;
import com.siival.bot.modules.bsc.service.UserWheelLogService;
import com.siival.bot.modules.bsc.service.dto.UserWheelLogQueryCriteria;
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
* @date 2022-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "大转盘记录管理")
@RequestMapping("/bsc/userWheelLog")
public class UserWheelLogController {

    private final UserWheelLogService userWheelLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userWheelLog:list')")
    public void exportUserWheelLog(HttpServletResponse response, UserWheelLogQueryCriteria criteria) throws IOException {
        userWheelLogService.download(userWheelLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询大转盘记录")
    @ApiOperation("查询大转盘记录")
    @PreAuthorize("@el.check('userWheelLog:list')")
    public ResponseEntity<Object> queryUserWheelLog(UserWheelLogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userWheelLogService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增大转盘记录")
    @ApiOperation("新增大转盘记录")
    @PreAuthorize("@el.check('userWheelLog:add')")
    public ResponseEntity<Object> createUserWheelLog(@Validated @RequestBody UserWheelLog resources){
        return new ResponseEntity<>(userWheelLogService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改大转盘记录")
    @ApiOperation("修改大转盘记录")
    @PreAuthorize("@el.check('userWheelLog:edit')")
    public ResponseEntity<Object> updateUserWheelLog(@Validated @RequestBody UserWheelLog resources){
        userWheelLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除大转盘记录")
    @ApiOperation("删除大转盘记录")
    @PreAuthorize("@el.check('userWheelLog:del')")
    public ResponseEntity<Object> deleteUserWheelLog(@RequestBody Integer[] ids) {
        userWheelLogService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}