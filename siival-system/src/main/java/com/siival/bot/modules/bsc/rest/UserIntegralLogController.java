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
import com.siival.bot.modules.bsc.domain.UserIntegralLog;
import com.siival.bot.modules.bsc.service.UserIntegralLogService;
import com.siival.bot.modules.bsc.service.dto.UserIntegralLogQueryCriteria;
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
@Api(tags = "积分历史管理")
@RequestMapping("/bsc/userIntegralLog")
public class UserIntegralLogController {

    private final UserIntegralLogService userIntegralLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userIntegralLog:list')")
    public void exportUserIntegralLog(HttpServletResponse response, UserIntegralLogQueryCriteria criteria) throws IOException {
        userIntegralLogService.download(userIntegralLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询积分历史")
    @ApiOperation("查询积分历史")
    @PreAuthorize("@el.check('userIntegralLog:list')")
    public ResponseEntity<Object> queryUserIntegralLog(UserIntegralLogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userIntegralLogService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分历史")
    @ApiOperation("新增积分历史")
    @PreAuthorize("@el.check('userIntegralLog:add')")
    public ResponseEntity<Object> createUserIntegralLog(@Validated @RequestBody UserIntegralLog resources){
        return new ResponseEntity<>(userIntegralLogService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分历史")
    @ApiOperation("修改积分历史")
    @PreAuthorize("@el.check('userIntegralLog:edit')")
    public ResponseEntity<Object> updateUserIntegralLog(@Validated @RequestBody UserIntegralLog resources){
        userIntegralLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除积分历史")
    @ApiOperation("删除积分历史")
    @PreAuthorize("@el.check('userIntegralLog:del')")
    public ResponseEntity<Object> deleteUserIntegralLog(@RequestBody Integer[] ids) {
        userIntegralLogService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}