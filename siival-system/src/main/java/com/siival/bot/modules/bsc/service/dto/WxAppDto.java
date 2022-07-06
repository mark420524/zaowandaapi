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
* @author Mark
* @date 2022-04-02
**/
@Data
public class WxAppDto implements Serializable {

    /** ID */
    private Integer id;

    /** app id */
    private String appId;

    /** app secret */
    private String appSecret;

    /** token */
    private String appToken;

    /** aes加密key */
    private String aesKey;

    /** 消息格式 */
    private String msgDataFormat;

    /** 类型 */
    private Integer type;

    /** 名称 */
    private String name;

    /** 备注 */
    private String remark;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}