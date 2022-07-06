package com.siival.bot;

import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName TestRedis
 * 
 * @Date 2022-03-21 16:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRedis {
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testSet(){
//        Set<Object> sGet = redisUtils.sGet(  RedisKeyConstant.TOKEN_MEMBER_SET);
//        System.out.println(sGet);
        String header = "442de8a8344540aeb37e73e7176ff504";
//        redisUtils.sSet(RedisKeyConstant.TOKEN_MEMBER_SET,header);

        String dayLimit = RedisKeyConstant.TOKEN_DAY_LIMIT + header;
        redisUtils.set(dayLimit,5);
    }

}
