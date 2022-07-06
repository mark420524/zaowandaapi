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
* @date 2022-04-21
**/
@Entity
@Data
@Table(name="sys_wheel_info")
public class WheelInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "奖项名称")
    private String name;

    @Column(name = "integral",nullable = false)
    @NotNull
    @ApiModelProperty(value = "奖项赠送积分")
    private Integer integral;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Column(name = "start_num",nullable = false)
    @NotNull
    @ApiModelProperty(value = "随机数起始值")
    private Integer startNum;

    @Column(name = "sort",nullable = false)
    @NotNull
    @ApiModelProperty(value = "排序值")
    private Integer sort;

    @Column(name = "end_num",nullable = false)
    @NotNull
    @ApiModelProperty(value = "随机数结束值")
    private Integer endNum;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(WheelInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}