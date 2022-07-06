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
package com.siival.bot.modules.bsc.service;

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.UserSignInfo;
import com.siival.bot.modules.bsc.domain.UserInfo;
import com.siival.bot.modules.bsc.domain.UserIntegralLog;
import com.siival.bot.modules.bsc.domain.UserSignLog;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.dto.UserInfoDto;
import com.siival.bot.modules.bsc.service.dto.UserInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @description 服务接口
* @author mark
* @date 2022-02-21
**/
public interface UserInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserInfoQueryCriteria criteria, Pageable pageable);

    /**
     * 查询数据分页
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String,Object> queryAllByParams(UserInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UserInfoDto>
    */
    List<UserInfoDto> queryAll(UserInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return UserInfoDto
     */
    UserInfoDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return UserInfoDto
    */
    UserInfoDto create(UserInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserInfo resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<UserInfoDto> all, HttpServletResponse response) throws IOException;

    UserSignInfo findUserSignInfo(BaseReq req);

    String saveUserSignInfo(BaseReq req);

    UserIntegralLog buildUserIntegralLog(Integer before, Integer after, Integer integral, Integer uid, String s, Integer type);

    UserSignLog buildUserSignLog(Integer uid, Integer integral);

    Integer findUserIntegral(Integer uid);

    void saveUserIntegralLog(ModifyUserIntegralReq req);
}