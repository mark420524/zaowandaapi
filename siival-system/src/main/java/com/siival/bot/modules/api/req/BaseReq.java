package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName BaseReq
 * 
 * @Date 2022-02-12 14:41
 */
@Data
public class BaseReq {

    protected Integer uid;
    protected Integer page=0;
    protected Integer size=10;
}
