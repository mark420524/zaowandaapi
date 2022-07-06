package com.siival.bot.modules.quartz.task;

import com.siival.bot.modules.bsc.service.QuestionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName RefreshQuestionTask
 * 
 * @Date 2022-03-29 17:52
 */
@Slf4j
@Async
@Component
public class RefreshQuestionTask {

    @Autowired
    private QuestionInfoService questionInfoService;

    public void handlerRefreshQuestion(){
        log.info("每日一题刷新开始");
        questionInfoService.refreshTodayQuestion();
        log.info("每日一题刷新结束");
    }
}
