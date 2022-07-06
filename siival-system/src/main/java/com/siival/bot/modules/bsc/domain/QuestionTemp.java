package com.siival.bot.modules.bsc.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionTemp
 * 
 * @Date 2022-03-09 16:54
 */
@Entity
@Data
@Table(name="question_temp")
public class QuestionTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "pid",nullable = false)
    @NotNull
    @ApiModelProperty(value = "上级分类")
    private Integer pid;

    @Column(name = "status",nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Integer status;

    @Column(name = "answer_desc")
    @ApiModelProperty(value = "答案解析")
    private String answerDesc;



    @Column(name = "solution")
    @ApiModelProperty(value = "答案")
    private String solution;

    @Column(name = "question")
    @ApiModelProperty(value = "问题")
    private String question;

    @Column(name = "options")
    @ApiModelProperty(value = "问题")
    private String options;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "创建时间")
    private Timestamp updateTime;
}
