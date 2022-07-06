package com.siival.bot.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionsInfoTypeEnum
 * 
 * @Date 2022-02-22 12:13
 */

public enum QuestionsInfoTypeEnum {
    OPTIONS(1, "选择题"),
    SHOW_QUESTION(2, "简答题"),
    TRUE_OR_FALSE(3, "判断题")
    ;
    Integer code;
    String desc;
    QuestionsInfoTypeEnum(Integer code,String desc){
        this.code=code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }
}
