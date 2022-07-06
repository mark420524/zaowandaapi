package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.util.List;

@Data
public class UserWheelInfo {

    private boolean alreadyWheel;
    private Integer integral;
    private List<SysWheelInfoVo> lotteryInfo;
}
