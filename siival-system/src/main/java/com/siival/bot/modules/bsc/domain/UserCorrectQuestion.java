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
package com.siival.bot.modules.bsc.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author mark
* @date 2022-02-24
**/
@Entity
@Data
@Table(name="user_correct_question")
public class UserCorrectQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "pid",nullable = false)
    @NotNull
    @ApiModelProperty(value = "题库分类id")
    private Integer pid;

    @ManyToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "题目")
    @JoinColumn(name = "questionId",referencedColumnName = "id")
    private QuestionInfo question;

    @ManyToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "用户id")
    @JoinColumn(name = "uid",referencedColumnName = "id")
    private WxUser user;

    @Column(name = "reason",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "纠错原因")
    private String reason;

    @Column(name = "status")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Column(name = "check_reason")
    @ApiModelProperty(value = "审核不通过原因")
    private String checkReason;

    @Column(name = "check_uid")
    @ApiModelProperty(value = "管理员用户id")
    private Integer checkUid;

    @Column(name = "check_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp checkTime;

    @Column(name = "create_time",nullable = false)
    @NotNull
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(UserCorrectQuestion source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}