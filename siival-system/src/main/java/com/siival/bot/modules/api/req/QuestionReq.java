package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionReq
 * 
 * @Date 2022-02-12 18:39
 */
@Data
public class QuestionReq  extends BaseReq {

    private Integer cid;
    private Integer id;
    //1 收藏 0-移除收藏  1-添加错题, 0-移除错题
    private Integer type;
}
