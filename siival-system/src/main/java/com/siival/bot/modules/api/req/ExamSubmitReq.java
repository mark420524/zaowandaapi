package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamSubmitReq
 * 
 * @Date 2022-02-21 9:59
 */
@Data
public class ExamSubmitReq extends BaseReq {

    private Integer cid;
    private Integer right;
    private Integer wrong;
}
