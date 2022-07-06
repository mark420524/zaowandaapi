package com.siival.bot.resp;

import com.alibaba.fastjson.JSON;
import com.siival.bot.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName R
 * 
 * @Date 2022/1/16 18:36
 */
@Data
public class R {

    private Integer code;
    private String message;
    private Object data;
    public static R success() {
        R r = new R();
        r.setCode(0);
        r.setMessage("success");
        return r;
    }

    public static R success(Object data) {
        R r = new R();
        r.setCode(0);
        r.setMessage("success");
        r.setData(data);
        return r;
    }

    public static R error() {

        return error("error");
    }

    public static R error(String message) {
        R r = new R();
        r.setCode(-1);
        r.setMessage(message);
        return r;
    }

    public static R error(ErrorCodeEnum code) {
        R r = new R();
        r.setCode(code.code());
        r.setMessage(code.msg());
        return r;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
