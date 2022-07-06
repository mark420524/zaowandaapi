package com.siival.bot.enums;


public enum IntegralTypeEnum {

    ADD_INTEGRAL (1, "增加"),
    REDUCE_INTEGRAL (2, "减少")

    ;
    Integer type;
    String desc;
    IntegralTypeEnum(Integer type,String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }
}
