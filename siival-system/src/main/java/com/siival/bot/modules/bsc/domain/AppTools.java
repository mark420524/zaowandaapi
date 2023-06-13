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
package com.siival.bot.modules.bsc.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.siival.bot.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
*
* @Description  /
* @author Mark
* @date 2022-07-29
**/
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="app_tools")
public class AppTools extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @Column(name = "text",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "显示文字")
    private String text;

    @Column(name = "path",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "跳转路径")
    private String path;

    @Column(name = "app_id",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "小程序appId")
    private String appId;

    @Column(name = "icon_color",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "图标颜色")
    private String iconColor;

    @Column(name = "icon",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "图标")
    private String icon;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Column(name = "sort",nullable = false)
    @NotNull
    @ApiModelProperty(value = "排序值")
    private Integer sort;


    public void copy(AppTools source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}