package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName NotifyInfo
 * 
 * @Date 2022-02-26 14:11
 */
@Data
public class NotifyInfo {

    private String message;
    private String title;

    private int stopService;
    private int id;
    //用户账号是否封禁
    private boolean forbidden;

}
