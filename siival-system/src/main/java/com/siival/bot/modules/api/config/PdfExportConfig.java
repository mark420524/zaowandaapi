package com.siival.bot.modules.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName PdfExportConfig
 * @Description
 * @Date 2022-03-10 11:33
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pdf.export")
public class PdfExportConfig {


    private String author ;
    private String subject  ;
    private String keyword  ;
    private String creator ;
    private String waterMark ;
    private String password;
    private String slogan;
    private String barcode;

}
