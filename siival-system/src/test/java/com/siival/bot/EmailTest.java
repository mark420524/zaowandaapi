package com.siival.bot;

import com.siival.bot.domain.vo.EmailVo;
import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.UserExportLogService;
import com.siival.bot.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName EmailTest
 * 
 * @Date 2022-03-14 10:03
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailTest {


    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private UserExportLogService userExportLogService;

    @Test
    public void testSendEmail() {
//        EmailParam req = new EmailParam();
//        req.setToEmail(new String[]{"markjava@foxmail.com"});
//        req.setSubject("test");
//        req.setFromEmail(sendEmail);
//        req.setContent("this is a test");
//        String fileName = "E:\\tiku\\PDFDemo.pdf";
//        try {
//            EmailUtils.sendEmail(jms,req,fileName);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

        EmailVo vo = new EmailVo();
        vo.setSubject("test");
        vo.setContent("答题测试");
        List<String> tos = new ArrayList<>();
        tos.add("markjava@foxmail.com");
        vo.setTos(tos);
        String fileName = "E:\\tiku\\PDFDemo.pdf";
        vo.setFileName(fileName);
        emailService.send(vo);
    }

    @Test
    public void testSend() {
        questionInfoService.handlerExportQuestion();
    }
    @Test
    public void testDownloadExam() {
        examInfoService.handlerExportExam();
    }

    @Test
    public void testExportPdf() {
        userExportLogService.exportPdf();
    }
}
