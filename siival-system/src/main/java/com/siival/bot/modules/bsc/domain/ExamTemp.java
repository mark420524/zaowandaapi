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
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website
* @description /
* @author Mark
* @date 2022-03-31
**/
@Entity
@Data
@Table(name="exam_temp")
public class ExamTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "href_html")
    @ApiModelProperty(value = "hrefHtml")
    private String hrefHtml;

    @Column(name = "exam_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "examName")
    private String examName;

    @Column(name = "status")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "file_url",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "rar文件路径")
    private String fileUrl;

    @Column(name = "handler_file_url",nullable = false)
    @ApiModelProperty(value = "程序处理文件路径")
    private String handlerFileUrl;

    @Column(name = "version")
    @ApiModelProperty(value = "教材版本version")
    private String version;

    @Column(name = "pid")
    @ApiModelProperty(value = "pid")
    private Integer pid;

    public void copy(ExamTemp source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}