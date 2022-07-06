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

import com.siival.bot.modules.bsc.domain.QuestionInfo;
import com.siival.bot.modules.bsc.domain.WxUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author mark
* @date 2022-02-24
**/
@Data
public class UserCorrectQuestionDto implements Serializable {

    private Integer id;

    /** 题库分类id */
    private Integer pid;

    private QuestionInfo question;

    private WxUser user;


    /** 纠错原因 */
    private String reason;

    /** 状态 */
    private Integer status;

    /** 审核不通过原因 */
    private String checkReason;

    /** 管理员用户id */
    private Integer checkUid;

    /** 创建时间 */
    private Timestamp checkTime;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}