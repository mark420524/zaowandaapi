package com.siival.bot.enums;


/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName CommonStatusEnum
 * 
 * @Date 2022/1/17 10:30
 */
public enum CommonStatusEnum {

    ENABLE(1,"启用"),
    DISABLE(0, "禁用")
    ;
    Integer key;
    String desc;
    CommonStatusEnum(Integer key, String desc ) {
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }
}
