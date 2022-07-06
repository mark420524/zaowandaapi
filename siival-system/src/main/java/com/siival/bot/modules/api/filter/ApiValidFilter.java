package com.siival.bot.modules.api.filter;

import com.alibaba.fastjson.JSON;
import com.siival.bot.enums.ErrorCodeEnum;
import com.siival.bot.modules.api.constant.CommonConstant;
import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.bsc.service.SysTokenService;
import com.siival.bot.modules.security.security.TokenProvider;
import com.siival.bot.util.SendRUtil;
import com.siival.bot.utils.RedisUtils;
import com.siival.bot.utils.StringUtils;
import com.siival.bot.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ApiValidFilter
 * 
 * @Date 2022-03-21 13:35
 */
@Component
public class ApiValidFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysTokenService sysTokenService;
    @Autowired
    private TokenProvider provider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        logger.info("api filter uri:{}",uri);
        if (!uri.startsWith("/weixin/api")) {
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        boolean open = sysTokenService.getApiCheckTokenOpen();
        if (!open) {
            logger.info("api token check not open");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String header = request.getHeader(CommonConstant.FILTER_HEADER_TOKEN);
        if (StringUtils.isBlank(header)) {
            header = request.getHeader(CommonConstant.FILTER_API_HEADER_TOKEN);
        }
        logger.info("收到token请求:{}",header);
        if (StringUtils.isBlank(header)) {
            SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_BLANK);
            return;
        }
        boolean tokenExists = redisUtils.smemberExist(RedisKeyConstant.TOKEN_MEMBER_SET, header);
        if (!tokenExists) {
            SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_NOT_EXISTS);
            return;
        }
        boolean maxLimit = checkValidDayMaxLimit(header);
        if (maxLimit) {
            SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_REQUEST_MAX_LIMIT);
            return;
        }
        /* 前端已上线，默认开启 user token 检测 代码注释
        boolean checkUserTokenOpen = sysTokenService.getApiCheckUserTokenOpen();
        if (!checkUserTokenOpen) {
            logger.info("api check user token  not open");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

         */
        String userToken = request.getHeader(CommonConstant.HEADER_USER_TOKEN);

        BodyReaderHttpServletRequestWrapper bodyRequest = new BodyReaderHttpServletRequestWrapper(
                 request);
        Integer uid = bodyRequest.getUserId( );
        if (uid<=0) {
            logger.info("uid不存在 初始化操作,或者公共资源");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if (StringUtils.isBlank(userToken)) {
            SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_USER_BLANK);
            return;
        }

        try {
            logger.info("校验用户uid:{},token:{}",uid ,userToken);
            boolean right = validUserToken(uid,userToken);
            if (!right) {
                logger.info("校验用户错误");
                SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_USER_NO_RIGHTS);
                return ;
            }
        }catch (Exception e){
            logger.error("校验用户错误uid错误",e);
            SendRUtil.sendErrorCodeToClient(response, ErrorCodeEnum.API_CODE_HEADER_USER_NO_RIGHTS);
            return;
        }
        logger.info("userToken校验成功");
        filterChain.doFilter(bodyRequest,servletResponse);
    }

    private boolean validUserToken(Integer uid, String userToken) {

        String subject = provider.getClaims(userToken).getSubject();
        logger.info("token解析的值为:{}",uid);
        return Integer.valueOf(subject).equals(uid);
    }

    private boolean checkValidDayMaxLimit(String header) {
        String dayLimit = RedisKeyConstant.TOKEN_DAY_LIMIT + header;
        Object maxLimit = redisUtils.get(dayLimit);
        if (maxLimit==null) {
            return true;
        }

        String dayCount = RedisKeyConstant.TOKEN_DAY_REQUEST_COUNT+header;
        long a = redisUtils.addIncrement(dayCount);
        redisUtils.expire(dayCount,  TimeUtils.diffSecondNowToDate(TimeUtils.getTodayEndTime()));
        Long  max = 0L;
        max = Long.valueOf(maxLimit.toString());
        logger.info("token:{},请求次数:{},max count:{}",header,a,max);
        //每次请求判断加1判断 故max+1
        return a>max;
    }
}
