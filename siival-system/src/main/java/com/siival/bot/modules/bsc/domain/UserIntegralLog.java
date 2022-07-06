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
* @date 2022-02-21
**/
@Entity
@Data
@Table(name="user_integral_log")
public class UserIntegralLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(value = "用户")
    @JoinColumn(name = "uid",referencedColumnName = "id")
    private WxUser user;

    @Column(name = "integral")
    @ApiModelProperty(value = "积分")
    private Integer integral;

    @Column(name = "type")
    @ApiModelProperty(value = "类型")
    private Integer type;

    @Column(name = "before_count")
    @ApiModelProperty(value = "操作前数量")
    private Integer beforeCount;

    @Column(name = "after_count")
    @ApiModelProperty(value = "操作后数量")
    private Integer afterCount;

    @Column(name = "remark",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "create_time",nullable = false)
    @NotNull
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(UserIntegralLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}