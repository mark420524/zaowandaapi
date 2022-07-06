package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName SearchQuestionInfoReq
 * 
 * @Date 2022-03-01 10:01
 */
@Data
public class SearchQuestionInfoReq extends BaseReq {

    private Integer cid;
//    private Integer uid;
    private String keywords;
//    private Integer page = 0;
//    private Integer size = 10;
}
