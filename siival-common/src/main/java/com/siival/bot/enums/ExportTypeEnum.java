package com.siival.bot.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExportTypeEnum
 * 
 * @Date 2022-04-01 16:17
 */
public enum ExportTypeEnum {
    QUESTIONS(1,"题库"),
    EXAMS(2,"试卷"),
    PDF(3,"PDF文件")
    ;
    int code;
    String desc;
    ExportTypeEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
}
