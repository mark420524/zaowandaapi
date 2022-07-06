package com.siival.bot.modules.api.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionTypeEnum
 * 
 * @Date 2022-02-12 17:53
 */
public enum QuestionTypeEnum {
    // type 1 答题 2 错题  3 收藏 4 模拟考试  5 分享题目 6 每日一题
    START_ANSWER(1, "答题"),
    WRONG_QUESTIONS(2, "错题"),
    FAVORITE(3, "收藏"),
    EXAM(4, "模拟考试"),
    SHARE_QUESTION(5, "分享题目"),
    TODAY_QUESTION(6,"每日一题")
    ;
    Integer type;
    String desc;
    QuestionTypeEnum(Integer type,String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static QuestionTypeEnum getInstanceByType(Integer type) {
        for (QuestionTypeEnum qte : QuestionTypeEnum.values()) {
            if (qte.type.equals(type)) {
                return qte;
            }
        }
        return START_ANSWER;
    }


}
