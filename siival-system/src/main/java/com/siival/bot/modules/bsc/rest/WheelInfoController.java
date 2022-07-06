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
import com.siival.bot.modules.bsc.domain.WheelInfo;
import com.siival.bot.modules.bsc.service.WheelInfoService;
import com.siival.bot.modules.bsc.service.dto.WheelInfoQueryCriteria;
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
* @date 2022-04-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "大转盘奖项管理")
@RequestMapping("/bsc/wheelInfo")
public class WheelInfoController {

    private final WheelInfoService wheelInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wheelInfo:list')")
    public void exportWheelInfo(HttpServletResponse response, WheelInfoQueryCriteria criteria) throws IOException {
        wheelInfoService.download(wheelInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询大转盘奖项")
    @ApiOperation("查询大转盘奖项")
    @PreAuthorize("@el.check('wheelInfo:list')")
    public ResponseEntity<Object> queryWheelInfo(WheelInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wheelInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增大转盘奖项")
    @ApiOperation("新增大转盘奖项")
    @PreAuthorize("@el.check('wheelInfo:add')")
    public ResponseEntity<Object> createWheelInfo(@Validated @RequestBody WheelInfo resources){
        return new ResponseEntity<>(wheelInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改大转盘奖项")
    @ApiOperation("修改大转盘奖项")
    @PreAuthorize("@el.check('wheelInfo:edit')")
    public ResponseEntity<Object> updateWheelInfo(@Validated @RequestBody WheelInfo resources){
        wheelInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除大转盘奖项")
    @ApiOperation("删除大转盘奖项")
    @PreAuthorize("@el.check('wheelInfo:del')")
    public ResponseEntity<Object> deleteWheelInfo(@RequestBody Integer[] ids) {
        wheelInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}