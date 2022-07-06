package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionReq
 * 
 * @Date 2022-02-12 14:40
 */
@Data
public class QuestionInfoListReq  extends BaseReq {
    // type 1 答题 2 错题  3 收藏 4 模拟考试 参考枚举 QuestionTypeEnum
    private Integer type;
    private Integer cid;
    private Integer number = 100;

    private Integer qid;
}
