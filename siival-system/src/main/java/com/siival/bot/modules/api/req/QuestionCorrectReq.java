package com.siival.bot.modules.api.req;

import lombok.Data;


@Data
public class QuestionCorrectReq extends BaseReq  {

    private Integer qid;
    private Integer cid;
    private String reason ;
}
