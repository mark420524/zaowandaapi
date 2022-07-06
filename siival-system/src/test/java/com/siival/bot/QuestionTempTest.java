package com.siival.bot;

import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.QuestionTempService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionTempTest
 * 
 * @Date 2022-03-09 17:09
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionTempTest {

    @Autowired
    private QuestionTempService questionTempService;
    @Autowired
    private QuestionInfoService questionInfoService;

    @Test
    public void testFindQuestionTemp(){
        questionTempService.saveHandlerQuestionTemp();
    }

    @Test
    public void testCreatePdf() {
        Integer pid = 3;
        String output = questionInfoService.createQuestionPdfFile(pid);
        System.out.println(output);
    }

    @Test
    public void testRefreshTodayQuestion() {
        questionInfoService.refreshTodayQuestion();
    }

    @Test
    public void testHandlerQuestionTemp(){
        questionTempService.handlerQuestionTemp();
    }
}
