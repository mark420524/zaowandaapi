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
* @date 2022-04-21
**/
@Data
public class WheelInfoDto implements Serializable {

    /** ID */
    private Integer id;

    /** 奖项名称 */
    private String name;

    /** 奖项赠送积分 */
    private Integer integral;

    /** 状态 */
    private Integer status;

    /** 随机数起始值 */
    private Integer startNum;

    /** 排序值 */
    private Integer sort;

    /** 随机数结束值 */
    private Integer endNum;

    /** 备注 */
    private String remark;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}