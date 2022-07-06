package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.SystemInitInfo;
import com.siival.bot.modules.api.service.SystemInitService;
import com.siival.bot.resp.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName StartController
 * 
 * @Date 2022-02-22 15:35
 */
@RestController
@RequestMapping("/weixin/api")
public class StartController {
    @Autowired
    private SystemInitService systemInitService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/init")
    public R initSystemInfo(BaseReq req ){
        logger.info("uid init once:{}",req.getUid());
        return R.success(systemInitService.getSystemInitInfo());
    }
}
