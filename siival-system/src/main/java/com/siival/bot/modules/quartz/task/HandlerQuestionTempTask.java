package com.siival.bot.modules.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName HandlerQuestitonTempTask
 * 
 * @Date 2022-03-09 16:51
 */
@Slf4j
@Async
@Component
public class HandlerQuestionTempTask {


    public void handlerQuestion() {
        log.info("处理临时问题start");
        log.info("处理临时问题end");
    }
}
