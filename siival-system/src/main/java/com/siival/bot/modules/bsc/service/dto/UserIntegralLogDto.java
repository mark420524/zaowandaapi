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

import com.siival.bot.modules.bsc.domain.WxUser;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author mark
* @date 2022-02-21
**/
@Data
public class UserIntegralLogDto implements Serializable {

    private Integer id;

    private WxUser user;

    /** 积分 */
    private Integer integral;

    /** 类型 */
    private Integer type;

    /** 操作前数量 */
    private Integer beforeCount;

    /** 操作后数量 */
    private Integer afterCount;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}