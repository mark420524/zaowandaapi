package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamScoreReq
 * 
 * @Date 2022-02-21 14:09
 */
@Data
public class ExamScoreReq {
    private Integer cid;
    private Integer page=0;
    private Integer size=20;
}
