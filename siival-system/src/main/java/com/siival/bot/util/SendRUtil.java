package com.siival.bot.util;

import com.siival.bot.enums.ErrorCodeEnum;
import com.siival.bot.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName SendRUtil
 * 
 * @Date 2022-03-21 14:01
 */

public class SendRUtil {

    public static void sendErrorCodeToClient(HttpServletResponse response, ErrorCodeEnum code) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        R  obj = R.error(code);

        try {
            response.getWriter().println(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
