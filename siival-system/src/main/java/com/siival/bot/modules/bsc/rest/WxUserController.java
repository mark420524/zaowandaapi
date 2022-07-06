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
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.modules.bsc.service.WxUserService;
import com.siival.bot.modules.bsc.service.dto.WxUserQueryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
* @date 2022-01-16
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "微信用户管理")
@RequestMapping("/bsc/wxUser")
public class WxUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WxUserService wxUserService;

    private final UserInfoService userInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('wxUser:list')")
    public void exportWxUser(HttpServletResponse response, WxUserQueryCriteria criteria) throws IOException {
        wxUserService.download(wxUserService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询微信用户")
    @ApiOperation("查询微信用户")
    @PreAuthorize("@el.check('wxUser:list')")
    public ResponseEntity<Object> queryWxUser(WxUserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wxUserService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增微信用户")
    @ApiOperation("新增微信用户")
    @PreAuthorize("@el.check('wxUser:add')")
    public ResponseEntity<Object> createWxUser(@Validated @RequestBody WxUser resources){
        return new ResponseEntity<>(wxUserService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改微信用户")
    @ApiOperation("修改微信用户")
    @PreAuthorize("@el.check('wxUser:edit')")
    public ResponseEntity<Object> updateWxUser(@Validated @RequestBody WxUser resources){
        wxUserService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除微信用户")
    @ApiOperation("删除微信用户")
    @PreAuthorize("@el.check('wxUser:del')")
    public ResponseEntity<Object> deleteWxUser(@RequestBody Integer[] ids) {
        wxUserService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/modifyIntegral")
    @Log("操作微信用户积分")
    @ApiOperation("操作微信用户积分")
    @PreAuthorize("@el.check('wxUser:modifyIntegral')")
    public ResponseEntity<Object> modifyUserIntegral(@Validated @RequestBody ModifyUserIntegralReq req) {
        logger.info("收到操作积分的请求:{}",req);
        userInfoService.saveUserIntegralLog(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}