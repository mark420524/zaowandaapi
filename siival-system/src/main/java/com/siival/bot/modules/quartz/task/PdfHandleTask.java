package com.siival.bot.modules.quartz.task;

import com.siival.bot.modules.bsc.service.UserExportLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName PdfHandleTask
 * @Description
 * @Date 2022-05-21 21:44
 */
@Slf4j
@Async
@Component
public class PdfHandleTask {

    @Autowired
    private UserExportLogService userExportLogService;


    public void handleSendPdf(){
        log.info("开始处理发送pdf的任务");
        userExportLogService.exportPdf();
        log.info("处理发送pdf的任务结束");
    }
}
