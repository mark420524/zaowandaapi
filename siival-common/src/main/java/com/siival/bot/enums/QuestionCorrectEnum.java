package com.siival.bot.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionCorrectEnum
 * 
 * @Date 2022-02-24 15:39
 */
public enum QuestionCorrectEnum {
    WAITING_CHECK(1,"用户提交纠错，待审核"),
    CHECK_SUCCESS(2, "审核通过"),
    CHECK_FAIL(3, "审核失败"),
    ;
    Integer code;
    String desc;
    QuestionCorrectEnum(Integer code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public Integer getCode() {
        return code;
    }
}
