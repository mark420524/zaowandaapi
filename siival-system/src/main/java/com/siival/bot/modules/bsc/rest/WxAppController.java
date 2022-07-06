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
import com.siival.bot.modules.bsc.domain.WxApp;
import com.siival.bot.modules.bsc.res.AppNameVo;
import com.siival.bot.modules.bsc.service.WxAppService;
import com.siival.bot.modules.bsc.service.dto.WxAppQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @author Mark
* @date 2022-04-02
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "微信app管理")
@RequestMapping("/bsc/wxApp")
public class WxAppController {

    private final WxAppService wxAppService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wxApp:list')")
    public void exportWxApp(HttpServletResponse response, WxAppQueryCriteria criteria) throws IOException {
        wxAppService.download(wxAppService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询微信app")
    @ApiOperation("查询微信app")
    @PreAuthorize("@el.check('wxApp:list')")
    public ResponseEntity<Object> queryWxApp(WxAppQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wxAppService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增微信app")
    @ApiOperation("新增微信app")
    @PreAuthorize("@el.check('wxApp:add')")
    public ResponseEntity<Object> createWxApp(@Validated @RequestBody WxApp resources){
        return new ResponseEntity<>(wxAppService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改微信app")
    @ApiOperation("修改微信app")
    @PreAuthorize("@el.check('wxApp:edit')")
    public ResponseEntity<Object> updateWxApp(@Validated @RequestBody WxApp resources){
        wxAppService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除微信app")
    @ApiOperation("删除微信app")
    @PreAuthorize("@el.check('wxApp:del')")
    public ResponseEntity<Object> deleteWxApp(@RequestBody Integer[] ids) {
        wxAppService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/enable/list")
    @Log("查询启用微信app")
    @ApiOperation("查询启用微信app")
    @PreAuthorize("@el.check('wxApp:list')")
    public ResponseEntity<Object> getEnableWxAppInfo() {
        List<AppNameVo> list = wxAppService.findEnableWxAppInfo();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}