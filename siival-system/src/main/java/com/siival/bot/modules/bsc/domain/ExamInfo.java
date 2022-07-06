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
* @date 2022-03-31
**/
@Entity
@Data
@Table(name="exam_info")
public class ExamInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "exam_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "examName")
    private String examName;

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

    @Column(name = "file_label")
    @ApiModelProperty(value = "filelabel")
    private String fileLabel;

    @Column(name = "file_size")
    @ApiModelProperty(value = "file 大小")
    private String fileSize;

    @Column(name = "version")
    @ApiModelProperty(value = "教材版本")
    private String version;

    @Column(name = "pid")
    @ApiModelProperty(value = "pid")
    private Integer pid;
    @Column(name = "tempId")
    @ApiModelProperty(value = "temp_id")
    private Integer tempId;
    @Column(name = "fileType")
    @ApiModelProperty(value = "file_type")
    private String fileType;

    public void copy(ExamInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}