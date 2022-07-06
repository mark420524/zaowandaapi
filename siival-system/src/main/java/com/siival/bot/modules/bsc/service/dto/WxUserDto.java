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
package com.siival.bot.modules.bsc.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author mark
* @date 2022-01-16
**/
@Data
public class WxUserDto implements Serializable {

    /** 唯一值 */
    private Integer id;

    /** 昵称 */
    private String nickName;

    /** 头像地址 */
    private String avatarUrl;

    /** 国家 */
    private String country;

    /** 性别 */
    private Integer gender;

    /** 语言 */
    private String language;

    /** 省份 */
    private String province;

    /** unionid */
    private String unionid;

    /** openid */
    private String openid;

    /** session key */
    private String sessionKey;

    /** 是否启用 */
    private Integer status;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}