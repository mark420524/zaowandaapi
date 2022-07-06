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
import com.siival.bot.modules.bsc.domain.UserSignLog;
import com.siival.bot.modules.bsc.service.UserSignLogService;
import com.siival.bot.modules.bsc.service.dto.UserSignLogQueryCriteria;
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
@Api(tags = "签到历史管理")
@RequestMapping("/bsc/userSignLog")
public class UserSignLogController {

    private final UserSignLogService userSignLogService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userSignLog:list')")
    public void exportUserSignLog(HttpServletResponse response, UserSignLogQueryCriteria criteria) throws IOException {
        userSignLogService.download(userSignLogService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询签到历史")
    @ApiOperation("查询签到历史")
    @PreAuthorize("@el.check('userSignLog:list')")
    public ResponseEntity<Object> queryUserSignLog(UserSignLogQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userSignLogService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增签到历史")
    @ApiOperation("新增签到历史")
    @PreAuthorize("@el.check('userSignLog:add')")
    public ResponseEntity<Object> createUserSignLog(@Validated @RequestBody UserSignLog resources){
        return new ResponseEntity<>(userSignLogService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改签到历史")
    @ApiOperation("修改签到历史")
    @PreAuthorize("@el.check('userSignLog:edit')")
    public ResponseEntity<Object> updateUserSignLog(@Validated @RequestBody UserSignLog resources){
        userSignLogService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除签到历史")
    @ApiOperation("删除签到历史")
    @PreAuthorize("@el.check('userSignLog:del')")
    public ResponseEntity<Object> deleteUserSignLog(@RequestBody Integer[] ids) {
        //userSignLogService.deleteAll(ids);
        //DO NOTHING
        return new ResponseEntity<>(HttpStatus.OK);
    }
}