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
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author mark
* @date 2022-01-16
**/
@Entity
@Data
@Table(name="wx_user")
public class WxUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "唯一值")
    private Integer id;

    @Column(name = "nick_name",nullable = false)
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @Column(name = "avatar_url")
    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @Column(name = "country")
    @ApiModelProperty(value = "国家")
    private String country;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别")
    private Integer gender;

    @Column(name = "language")
    @ApiModelProperty(value = "语言")
    private String language;

    @Column(name = "province")
    @ApiModelProperty(value = "省份")
    private String province;

    @Column(name = "unionid")
    @ApiModelProperty(value = "unionid")
    private String unionid;

    @Column(name = "openid")
    @ApiModelProperty(value = "openid")
    private String openid;

    @Column(name = "session_key")
    @ApiModelProperty(value = "session key")
    private String sessionKey;

    @Column(name = "status")
    @ApiModelProperty(value = "是否启用")
    private Integer status;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "app_id" )
    @ApiModelProperty(value = "app id")
    private String appId;

    public void copy(WxUser source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}