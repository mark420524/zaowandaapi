package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserLoginRes
 * @Description
 * @Date 2022-04-21 21:39
 */
@Data
public class UserLoginRes {

    private Integer userId;
    private String token;
}
