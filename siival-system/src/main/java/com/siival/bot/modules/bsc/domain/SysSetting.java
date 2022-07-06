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

import com.siival.bot.base.BaseEntity;
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
* @date 2022-05-06
**/
@Entity
@Data
@Table(name="sys_setting")
public class SysSetting extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = BaseEntity.Update.class)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @Column(name = "name",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设置key")
    private String name;

    @Column(name = "value",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设置值")
    private String value;

    @Column(name = "remark",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "描述")
    private String remark;

    public void copy(SysSetting source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}