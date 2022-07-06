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
* @author Mark
* @date 2022-03-29
**/
@Entity
@Data
@Table(name="sys_token")
public class SysToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "token")
    @ApiModelProperty(value = "token值")
    private String token;

    @Column(name = "day_max_limit",nullable = false)
    @NotNull
    @ApiModelProperty(value = "每天最大请求次数")
    private Integer dayMaxLimit;

    @Column(name = "thread_limit",nullable = false)
    @NotNull
    @ApiModelProperty(value = "并发限制次数")
    private Integer threadLimit;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @OneToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "微信app")
    @JoinColumn(name = "wx_app_id",referencedColumnName = "id")
    private WxApp wxApp;

    public void copy(SysToken source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}