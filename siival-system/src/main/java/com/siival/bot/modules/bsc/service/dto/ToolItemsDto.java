/*
*  Copyright 2019-2020
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

import java.io.Serializable;
import java.sql.Timestamp;

/**
* 
* @Description  /
* @author Mark
* @date 2022-07-29
**/
@Data
public class ToolItemsDto implements Serializable {

    /** ID */
    private Integer id;

    /** 显示文字 */
    private String text;

    /** 跳转url */
    private String url;

    /** 跳转类型 */
    private String linkType;

    /** 图标颜色 */
    private String iconColor;

    /** 图标 */
    private String icon;

    /** 状态 */
    private Integer status;

    /** 排序值 */
    private Integer sort;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}