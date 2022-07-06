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
* @date 2022-03-11
**/
@Entity
@Data
@Table(name="user_export_log")
public class UserExportLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "uid",nullable = false)
    @NotNull
    @ApiModelProperty(value = "用户id")
    private Integer uid;

    @Column(name = "integral")
    @ApiModelProperty(value = "消耗积分")
    private Integer integral;

    @Column(name = "pid")
    @ApiModelProperty(value = "题库id")
    private Integer pid;

    @Column(name = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Column(name = "status")
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
    @Column(name = "export_type")
    @ApiModelProperty(value = "导出类型")
    private Integer exportType;

    @Column(name = "exam_id")
    @ApiModelProperty(value = "试卷id")
    private Integer examId;
    @Column(name = "file_path")
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    public void copy(UserExportLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}