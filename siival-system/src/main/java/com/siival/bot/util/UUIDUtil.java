package com.siival.bot.util;

import cn.hutool.core.lang.UUID;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UUIDUtil
 * 
 * @Date 2022-03-29 15:30
 */

public class UUIDUtil {

    public static String getRandomUUID(){
        UUID u = UUID.randomUUID();
        return u.toString(true);
    }
}
