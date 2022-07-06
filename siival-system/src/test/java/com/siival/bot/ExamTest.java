package com.siival.bot;

import com.siival.bot.modules.bsc.service.ExamTempService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamTest
 * 
 * @Date 2022-03-31 15:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExamTest {
    @Autowired
    private ExamTempService examTempService;

    @Test
    public void testExamTemp() {
        examTempService.handlerExamTemp();
    }
}
