package com.siival.bot;

import com.siival.bot.modules.security.security.TokenProvider;
import com.siival.bot.modules.security.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginCacheTest {

    @Resource(name = "userDetailsService")
    private UserDetailsServiceImpl userDetailsService;
    ExecutorService executor = Executors.newCachedThreadPool();
    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void testCache() throws InterruptedException {
        long start1 = System.currentTimeMillis();
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            executor.submit(() -> userDetailsService.loadUserByUsername("admin"));
            latch.countDown();
        }
        latch.await();

        long end1 = System.currentTimeMillis();
        //关闭缓存
        userDetailsService.setEnableCache(false);
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            userDetailsService.loadUserByUsername("admin");
        }
        long end2 = System.currentTimeMillis();
        System.out.print("使用缓存：" + (end1 - start1) + "毫秒\n 不使用缓存：" + (end2 - start2) + "毫秒");
    }

    @Test
    public void testToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyM2QyMWQzOTY2ZWU0NTE5OWUzZDE1ZWE1NDI1N2U0NiIsInVzZXIiOjUsInN1YiI6IjUifQ.R7aTI8u_UXtIIFoLM-8ST7Hp7cjixhYQO0HmPifwfPldH0PY5ngfvwGodEO7skV770Xoucrse0ijfC5TltcI3Q";
        String subject = tokenProvider.getClaims(token).getSubject();
        System.out.println(subject);
        String newToke = tokenProvider.createToken(5);
        System.out.println(newToke);
        System.out.println(newToke.equals(token));
        newToke = tokenProvider.createToken(5);
        System.out.println(newToke);
    }

}
