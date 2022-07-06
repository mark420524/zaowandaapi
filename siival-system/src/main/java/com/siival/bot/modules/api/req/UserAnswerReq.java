package com.siival.bot.modules.api.req;


import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserAnswerReq
 * 
 * @Date 2022-03-26 17:00
 */
@Data
public class UserAnswerReq extends BaseReq {

    private String userAnswer;

    public String toString(){
        return JSONUtil.toJsonStr(this);
    }
}
