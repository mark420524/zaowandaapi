package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.req.UserInfo;
import com.siival.bot.modules.api.resp.*;
import com.siival.bot.modules.bsc.service.*;
import com.siival.bot.resp.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserController
 * @Description
 * @Date 2022-06-25 10:13
 */
@RestController
@RequestMapping("/weixin/api/user")
public class UserApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserIntegralLogService userIntegralLogService;
    @Autowired
    private UserInviteLogService userInviteLogService;
    @Autowired
    private UserWheelLogService userWheelLogService;

    @PostMapping("/updateUser")
    public R updateUserInfo(UserInfo info) {
        logger.info("收到更新用户信息的请求:{}",info);
        try {
            wxUserService.updateUserInfo(info);
            return R.success(1);
        } catch (Exception e) {
            logger.error("更新客户信息失败:{}", info, e);
            return R.success(0);
        }

    }
    /**
     * 微信获取用户信息方式改了，要改下
     * @author mark
     * @description TODO
     * @date 2022-06-25 10:21
     * @param info
     * @return com.siival.bot.resp.R
     */
    @PostMapping("/v2/updateUser")
    public R updateUserInfoV2(UserInfo info) {
        logger.info("收到更新用户信息的请求:{}",info);
        try {
            String msg =  wxUserService.updateUserInfo(info);
            return R.success(msg);
        } catch (Exception e) {
            logger.error("更新客户信息失败:{}", info, e);
            return R.error("更新客户信息失败");
        }

    }



    @PostMapping("/signin")
    public R userSignin(BaseReq req){
        return R.success(userInfoService.saveUserSignInfo(req));
    }


    @PostMapping("/sign/info")
    public R userSignList(BaseReq req){
        UserSignInfo usi = userInfoService.findUserSignInfo(  req);
        return R.success(usi);
    }
    @PostMapping("/integral")
    public R userIntegral(BaseReq req) {
        Integer count = userInfoService.findUserIntegral(req.getUid());
        return R.success(count);
    }


    @PostMapping("/integral/list")
    public R userIntegralLogList(BaseReq req) {
        List<UserIntegralLogVo> list = userIntegralLogService.findIntegralLogByPage(req);
        return R.success(list);
    }

    @PostMapping("/invite/list")
    public R userInviteLogList(BaseReq req) {
        List<UserInviteLogVo> list =  userInviteLogService.findUserInviteLogVoByReq(req);
        return R.success(list);
    }

    @GetMapping("/integral/wheel")
    public R getUserWheelIntegralInfo(BaseReq req) {
        UserWheelInfo info = userWheelLogService.queryUserTodayWheelInfo(req);
        return R.success(info);
    }

    @PostMapping("/integral/wheel")
    public R userWheelIntegral(BaseReq req) {
        UserWheelResult msg  = userWheelLogService.saveUserWheelLog(req);
        return R.success(msg);
    }
}
