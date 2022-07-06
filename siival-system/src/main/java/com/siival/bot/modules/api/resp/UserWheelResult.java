package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserWheelResult
 * @Description
 * @Date 2022-04-22 21:35
 */

@Data
public class UserWheelResult {

    private Integer index;
    private Integer integral;
    private boolean alreadyWheel;
    private String message;
}
