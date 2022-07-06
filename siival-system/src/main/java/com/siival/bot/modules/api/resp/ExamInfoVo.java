package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamInfoVo
 * 
 * @Date 2022-03-31 16:44
 */
@Data
public class ExamInfoVo {

    private Integer id;
    private String fileType;
    private String fileSize;
    private String version;
    private String examName;
}
