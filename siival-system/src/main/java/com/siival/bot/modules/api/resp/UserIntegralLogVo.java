package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserIntegralLogVo
 * 
 * @Date 2022-02-22 20:26
 */
@Data
public class UserIntegralLogVo {

    private Integer integral;
    private Integer type ;
    private Integer beforeCount;
    private Integer afterCount;
    private String remark ;
    private String createTime;
}
