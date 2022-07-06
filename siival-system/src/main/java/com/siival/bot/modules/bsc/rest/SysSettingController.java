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
import com.siival.bot.modules.bsc.domain.SysSetting;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.dto.SysSettingQueryCriteria;
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
* @date 2022-05-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "系统设置管理")
@RequestMapping("/bsc/sysSetting")
public class SysSettingController {

    private final SysSettingService sysSettingService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysSetting:list')")
    public void exportSysSetting(HttpServletResponse response, SysSettingQueryCriteria criteria) throws IOException {
        sysSettingService.download(sysSettingService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询系统设置")
    @ApiOperation("查询系统设置")
    @PreAuthorize("@el.check('sysSetting:list')")
    public ResponseEntity<Object> querySysSetting(SysSettingQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysSettingService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增系统设置")
    @ApiOperation("新增系统设置")
    @PreAuthorize("@el.check('sysSetting:add')")
    public ResponseEntity<Object> createSysSetting(@Validated @RequestBody SysSetting resources){
        return new ResponseEntity<>(sysSettingService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改系统设置")
    @ApiOperation("修改系统设置")
    @PreAuthorize("@el.check('sysSetting:edit')")
    public ResponseEntity<Object> updateSysSetting(@Validated @RequestBody SysSetting resources){
        sysSettingService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除系统设置")
    @ApiOperation("删除系统设置")
    @PreAuthorize("@el.check('sysSetting:del')")
    public ResponseEntity<Object> deleteSysSetting(@RequestBody Integer[] ids) {
//        sysSettingService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}