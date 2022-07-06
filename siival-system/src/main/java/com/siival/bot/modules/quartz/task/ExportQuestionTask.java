package com.siival.bot.modules.quartz.task;

import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExportQuestionTask
 * 
 * @Date 2022-03-13 17:56
 */
@Slf4j
@Async
@Component
public class ExportQuestionTask {
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private ExamInfoService examInfoService;

    public void handlerExportQuestion() {
        log.info("开始处理导出题库功能");
        questionInfoService.handlerExportQuestion();
        log.info("处理导出题库功能结束");
    }

    public void handlerExportExam() {
        log.info("开始处理导出试卷功能");
        examInfoService.handlerExportExam();
        log.info("处理导出题库试卷结束");
    }
}
