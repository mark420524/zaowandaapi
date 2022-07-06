package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName TodayQuestionInfo
 * 
 * @Date 2022-03-26 15:04
 */

@Data
public class TodayQuestionInfo {

    private List<QuestionsVo> questions;
    private long totalCount;
    /**
     * 今天题目id
     */
    private Integer qid;
    /**
     * 赠送积分
     */
    private Integer integral;
    /**
     * 用户是否已经作答
     */
    private boolean alreadyAnswer;
    /**
     * 用户答题获得积分，0则代表当天答题错误
     */
    private Integer answerIntegral;
}
