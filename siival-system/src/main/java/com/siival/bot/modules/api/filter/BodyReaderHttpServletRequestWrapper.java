package com.siival.bot.modules.api.filter;


import com.siival.bot.utils.StringUtils;
import com.siival.bot.xss.HTMLFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * XSS过滤处理
 *
 * @author Mark mark420524@gmail.com
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    //没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
    HttpServletRequest orgRequest;

    Map<String,String[]> parameters  ;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;

        parameters = request.getParameterMap();

    }

    public Integer getUserId(){
        String userId = getParameter("uid");
        Integer uid = 0;
        try {
            uid = StringUtils.isBlank(userId) ? 0 : Integer.valueOf(userId);
        }catch (NumberFormatException e){
            // numberformat 不处理
        }

        return uid;
    }


    public String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());//将获取到的请求参数重新塞入request里面去
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Description: 复制输入流</br>
     *
     * @param inputStream
     * @return</br>
     */
    public InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
 
        String body = getBodyString(orgRequest);
        final ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);

        return values==null?null:values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = this.parameters.get (name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        return parameters;
    }

    @Override
    public Map<String,String[]> getParameterMap() {

        return this.parameters;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);

        return value;
    }

    
    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof BodyReaderHttpServletRequestWrapper) {
            return  ((BodyReaderHttpServletRequestWrapper)request).getOrgRequest();
        }

        return request;
    }

}
