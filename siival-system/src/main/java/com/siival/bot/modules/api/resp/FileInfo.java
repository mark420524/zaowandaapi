package com.siival.bot.modules.api.resp;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName FileInfo
 * @Description
 * @Date 2022-05-13 17:06
 */
@Data
public class FileInfo {

    private String domain;
    private String path;
    private Integer count;
    //用户积分
    private Integer integral;
}
