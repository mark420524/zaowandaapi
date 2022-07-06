package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.bsc.constant.SystemSettingConstant;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName SettingController
 * @Description
 * @Date 2022-05-06 18:23
 */
@RestController
@RequestMapping("/weixin/api/setting")
public class SettingController {

    @Autowired
    private SysSettingService sysSettingService;

    @GetMapping("/invite")
    public R getInviteIntegral() {
        Integer integral = sysSettingService.findSystemSettingValueToInteger(SystemSettingConstant.INVITE_SHARE_INTEGRAL);
        return R.success(integral);
    }

    @GetMapping("/qqgroup")
    public R getQQGroup() {
        String group = sysSettingService.findSystemSettingValue(SystemSettingConstant.CONTACT_QQ_GROUP);
        return R.success(group);
    }
}
