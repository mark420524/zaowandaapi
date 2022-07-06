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
import com.siival.bot.modules.bsc.domain.UserExportLog;
import com.siival.bot.modules.bsc.service.UserExportLogService;
import com.siival.bot.modules.bsc.service.dto.UserExportLogQueryCriteria;
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
* @date 2022-03-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "导出记录管理")
@RequestMapping("/bsc/userExportLog")
public class UserExportLogController {

    private final UserExportLogService userExportLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userExportLog:list')")
    public void exportUserExportLog(HttpServletResponse response, UserExportLogQueryCriteria criteria) throws IOException {
        userExportLogService.download(userExportLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询导出记录")
    @ApiOperation("查询导出记录")
    @PreAuthorize("@el.check('userExportLog:list')")
    public ResponseEntity<Object> queryUserExportLog(UserExportLogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userExportLogService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增导出记录")
    @ApiOperation("新增导出记录")
    @PreAuthorize("@el.check('userExportLog:add')")
    public ResponseEntity<Object> createUserExportLog(@Validated @RequestBody UserExportLog resources){
        return new ResponseEntity<>(userExportLogService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改导出记录")
    @ApiOperation("修改导出记录")
    @PreAuthorize("@el.check('userExportLog:edit')")
    public ResponseEntity<Object> updateUserExportLog(@Validated @RequestBody UserExportLog resources){
        userExportLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除导出记录")
    @ApiOperation("删除导出记录")
    @PreAuthorize("@el.check('userExportLog:del')")
    public ResponseEntity<Object> deleteUserExportLog(@RequestBody Integer[] ids) {
        userExportLogService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}