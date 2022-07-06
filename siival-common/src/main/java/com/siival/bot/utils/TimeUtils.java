package com.siival.bot.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName TimeUtils
 * 
 * @Date 2022-02-21 17:53
 */

public class TimeUtils {

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String SHORT_MONTH_FORMAT = "yyyy-MM";

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTodayStart(){
        LocalDateTime startTime = getTodayStartTime();
        long time = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() ;
        return new Timestamp(time);
    }

    public static LocalDateTime getTodayStartTime() {
        LocalDateTime startTime=LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return startTime;
    }

    public static LocalDateTime getTodayEndTime() {
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return endTime;
    }

    public static LocalDateTime getDayEndTime(LocalDate  start) {
        LocalDateTime endTime = LocalDateTime.of(start, LocalTime.MAX);
        return endTime;
    }


    public static LocalDateTime getAfterDayEndTime(long plusDay) {
        LocalDate now = LocalDate .now();
        now = now.plusDays(plusDay);
        return getDayEndTime(now);
    }


    public static long diffSecondNowToDate(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, date).getSeconds()  ;
    }

    public static String getTodayStr() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
        return df.format(localDate);
    }

    public static String getMonthStartStr() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern(SHORT_MONTH_FORMAT);
        return df.format(localDate) + "-01";
    }

    public static Date getMonthStartDate() throws ParseException {
        String monthStart = getMonthStartStr();
        SimpleDateFormat df = new SimpleDateFormat (SHORT_DATE_FORMAT);
        return df.parse(monthStart);
    }

    public static void main(String[] args) {
        String res = "1,2,3";

            System.out.println(getTodayStart());

    }
}
