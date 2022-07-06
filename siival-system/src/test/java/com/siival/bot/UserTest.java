package com.siival.bot;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.siival.bot.modules.bsc.service.WxUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserTest
 * 
 * @Date 2022-03-29 13:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @Autowired
    private WxUserService userService;

    @Test
    public void testInviteUser(){
        Integer userId  = 5;
        WxMaJscode2SessionResult result = new WxMaJscode2SessionResult();
        result.setOpenid("test131");
        userService.saveOrFindUserIdBySession(result,userId,null);
    }
}
