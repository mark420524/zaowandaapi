package com.siival.bot.modules.api.config;

import com.siival.bot.modules.bsc.service.WxAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName WxMaServiceRunner
 * 
 * @Date 2022-04-02 14:10
 */
@Component
public class WxMaServiceRunner implements ApplicationRunner {

    @Autowired
    private WxAppService wxAppService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动初始化微信app service配置开始");
        wxAppService.initEnableWxAppService();
        logger.info("项目启动初始化微信app service配置结束");
    }
}
