package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.util.Map;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserSignInfo
 * 
 * @Date 2022-02-21 17:13
 */
@Data
public class UserSignInfo {

    private String startTime;
    private String today;
    private Integer totalSign;
    private Integer continuousSign;
    private Map<String ,Integer > dayIntegral;
}
