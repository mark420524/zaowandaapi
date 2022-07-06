package com.siival.bot.enums;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName IntegralCountEnum
 * 
 * @Date 2022-02-21 17:57
 */

public enum IntegralCountEnum {

    ONE(1,1),
    TWO(2,2),
    THREE(3,3),
    FOUR(4,4),
    FIVE(5,5),
    SIX(6,6),
    SEVEN(7,7)
    ;
    Integer count;
    Integer integral;
    IntegralCountEnum(Integer count, Integer integral){
        this.count = count;
        this.integral = integral;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getIntegral() {
        return integral;
    }

    public static Integer getIntegralByCount(Integer count) {
        if (count==null || count<=0 ) {
            return 0;
        }
        if(count> SEVEN.getCount()) {
            return SEVEN.getIntegral();
        }
        for (IntegralCountEnum countEnum : IntegralCountEnum.values()) {
            if (countEnum.getCount().equals(count)) {
                return countEnum.getIntegral();
            }
        }
        return 0;
    }
}
