package com.siival.bot.modules.api.rest;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.siival.bot.modules.api.config.WxMaConfiguration;
import com.siival.bot.modules.api.config.WxMaProperties;
import com.siival.bot.modules.api.constant.CommonConstant;
import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.req.LoginReq;
import com.siival.bot.modules.api.req.UserInfo;
import com.siival.bot.modules.api.resp.*;
import com.siival.bot.modules.bsc.service.*;
import com.siival.bot.modules.security.security.TokenProvider;
import com.siival.bot.resp.R;
import com.siival.bot.utils.StringUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName WxAuthController
 * 
 * @Date 2022/1/16 18:31
 */
@RestController
@RequestMapping("/weixin/api")
public class WxAuthController {
    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private WxAppService wxAppService;
    @Autowired
    private TokenProvider provider;

    private Logger logger = LoggerFactory.getLogger(WxAuthController.class);

    @PostMapping("/v2/user/login/{appId}")
    public R codeToUserIdV2(LoginReq req,@PathVariable("appId") String appId) {
        String code = req.getCode();
        if (StringUtils.isAllBlank(code)) {
            return R.error("参数错误");
        }

        final WxMaService wxService = wxAppService.getWxAppService(appId);
        if (wxService==null) {
            return R.error("app尚未配置,请联系管理员配置");
        }

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            logger.info("请求获取openid结果:{}",session);
            if (session!=null) {
                Integer userId = wxUserService.saveOrFindUserIdBySession(session, req.getInviteUid(),appId);
                logger.info("openid:{},unionid:{},userid:{}", session.getOpenid(),session.getUnionid(), userId);
                UserLoginRes res = new UserLoginRes();
                res.setUserId(userId);
                res.setToken(provider.createToken(userId));
                return R.success(res);
            }else{
                return R.error("授权错误");
            }
        } catch (WxErrorException e) {
            logger.error(e.getMessage(), e);
            return R.error("请求登录错误");
        }
    }


}
