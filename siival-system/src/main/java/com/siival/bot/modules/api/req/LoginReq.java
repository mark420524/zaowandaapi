package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName LoginReq
 * 
 * @Date 2022-03-28 17:36
 */
@Data
public class LoginReq {

    private String code;
    private Integer inviteUid;
}
