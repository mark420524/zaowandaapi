package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamScoreRankVo
 * 
 * @Date 2022-02-21 13:15
 */
@Data
public class ExamScoreRankVo {

    private String nickName;
    private Integer score;
    private String avatarUrl;
    private String createTime;

    public ExamScoreRankVo(String nickName, Integer score, String avatarUrl, String createTime) {
        this.nickName = nickName;
        this.score = score;
        this.avatarUrl = avatarUrl;
        this.createTime = createTime;
    }

    public ExamScoreRankVo() {
    }
}
