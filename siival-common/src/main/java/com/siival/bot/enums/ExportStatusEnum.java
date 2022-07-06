package com.siival.bot.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExportStatusEnum
 * 
 * @Date 2022-03-11 9:23
 */

public enum ExportStatusEnum {
    WAITING(0,"用户提交"),
    SUCCESS(1, "成功导出"),
    FAILED(2,"导出失败");
    ;
    Integer status;
    String desc ;

    ExportStatusEnum(Integer status,String desc ){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }
}
