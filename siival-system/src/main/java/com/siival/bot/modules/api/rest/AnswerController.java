package com.siival.bot.modules.api.rest;


import com.siival.bot.modules.api.req.QuestionReq;
import com.siival.bot.modules.bsc.service.QuestionInfoService;

import com.siival.bot.resp.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName AnswerController
 * 
 * @Date 2022-02-12 18:36
 */
@RestController
@RequestMapping("/weixin/api/answer")
public class AnswerController {

    private Logger logger = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
    private QuestionInfoService questionInfoService;

    /**
     *
     * @author mark
     * @description 保存或移除错题
     * @date 2022-02-13 14:42
     * @param req
     * @return com.siival.bot.resp.R
     */
    @PostMapping("/wrong")
    public R saveOrRemoveWrongQuestion(QuestionReq req) {
        logger.info("收到保存错题的请求:{}",req);

        int success = 1;
        try {
            questionInfoService.saveUserWrongQuestion(req);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
        }
        return R.success(success);
    }

    /**
     * @author mark
     * @description 添加或者移除收藏
     * @date 2022-02-13 14:43
     * @param req
     * @return com.siival.bot.resp.R
     */
    @PostMapping("/favorite")
    public R favoriteOrNotQuestion (QuestionReq req) {
        logger.info("收到收藏的请求:{}",req);
        int success = 1;
        try {
            questionInfoService.saveUserFavoriteQuestion(req);
        } catch (Exception e) {
            e.printStackTrace();
            success = 0;
        }
        return R.success(success);
    }

    @PostMapping("/isfavorite")
    public R isFavoriteQuestion (QuestionReq req) {
        logger.info("收到查询是否收藏的请求:{}",req);

        return R.success(questionInfoService.findUserFavoriteQuestion(req));
    }

    @PostMapping("/favoriteCount")
    public R favoriteQuestionCount (QuestionReq req) {
        logger.info("收到查询用户收藏数量的请求:{}",req);

        return R.success(questionInfoService.findUserFavoriteQuestionCount(req));
    }


    @PostMapping("/wrongCount")
    public R wrongQuestionCount (QuestionReq req) {
        logger.info("收到查询用户错题数量的请求:{}",req);

        return R.success(questionInfoService.findUserWrongQuestionCount(req));
    }


}
