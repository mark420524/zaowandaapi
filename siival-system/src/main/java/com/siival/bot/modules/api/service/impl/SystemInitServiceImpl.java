package com.siival.bot.modules.api.service.impl;

import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.modules.api.resp.SystemInitInfo;
import com.siival.bot.modules.api.service.SystemInitService;
import com.siival.bot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName SystemInitServiceImpl
 * 
 * @Date 2022-02-22 15:39
 */
@Service
public class SystemInitServiceImpl implements SystemInitService {
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public SystemInitInfo getSystemInitInfo() {

        SystemInitInfo systemInitInfo = new SystemInitInfo();
        Object obj = redisUtils.get(RedisKeyConstant.QUESTION_MENU_REFRESH);
        obj = obj==null?"0":obj;
        systemInitInfo.setCategory(Integer.valueOf(obj.toString()));
        return systemInitInfo;
    }
}
