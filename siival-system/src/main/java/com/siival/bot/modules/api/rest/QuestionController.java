package com.siival.bot.modules.api.rest;


import cn.hutool.core.util.RandomUtil;
import com.siival.bot.enums.ErrorCodeEnum;
import com.siival.bot.modules.api.enums.QuestionTypeEnum;
import com.siival.bot.modules.api.req.*;
import com.siival.bot.modules.api.resp.QuestionsVo;
import com.siival.bot.modules.api.resp.TodayQuestionInfo;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.resp.PageResult;
import com.siival.bot.resp.R;
import com.siival.bot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionController
 * @Description 查询问题
 * @Date 2022/1/17 14:46
 */
@RestController
@RequestMapping("/weixin/api/question")
public class QuestionController {
    @Autowired
    private QuestionInfoService questionInfoService;

    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @PostMapping("/list")
    public R getQuestionInfo(QuestionInfoListReq req) {
        logger.info("收到查询问题的参数为:{}",req);
        if (req.getUid()==null) {
            return R.error("参数错误");
        }
        QuestionTypeEnum type = QuestionTypeEnum.getInstanceByType(req.getType());
        List<QuestionsVo> list = null;
        switch (type){
            case START_ANSWER:
            case EXAM:
                list = questionInfoService.findQuestionInfoVosByPid(req.getCid());
                break;
            case FAVORITE:
                //收藏
                list = questionInfoService.findUserFavoriteQuestionInfos(req);
                break;
            case WRONG_QUESTIONS:
                list = questionInfoService.findUserWrongQuestionInfos(req);
                break;
            case SHARE_QUESTION:
                list = questionInfoService.findShareQuestionByQid(req.getQid());
                break;
            default:break;
        }
        if (type==QuestionTypeEnum.EXAM) {
            //随机100题
            list = buildRandomList(list, req.getNumber());
        }
        return R.success(list);
    }

    private List<QuestionsVo> buildRandomList(List<QuestionsVo> list, Integer number) {
        if (list==null || list.size()<=number) {
            //小于等于直接返回
            return list;
        }
        Set<Integer> set = new HashSet<>();
        //开始随机
        List<QuestionsVo> newList = new ArrayList<>();
        int a = RandomUtil.randomInt(0,list.size());
        while (newList.size()!=number  ) {
            if(set.contains(a)){
                a = RandomUtil.randomInt(0,list.size());
                continue;
            }
            set.add(a);
            newList.add(list.get(a));
            a = RandomUtil.randomInt(0,list.size());
        }
        return newList;

    }


    @PostMapping("/count")
    public R getQuestionCountInfo(QuestionInfoListReq req) {
        if (req.getCid()==null) {
            return R.error("参数错误");
        }
        return R.success(questionInfoService.findQuestionCount(req.getCid()));
    }
    @PostMapping("/correct")
    public R saveQuestionUserCorrect(QuestionCorrectReq req){
        if (req.getQid()==null || req.getUid()==null || StringUtils.isBlank(req.getReason())) {
            return R.error("参数错误");
        }
        String msg = questionInfoService.saveUserQuestionCorrect(req);
        return R.success(msg);
    }

    @PostMapping("/search")
    public R searchQuestionInfo(SearchQuestionInfoReq req) {
        logger.info("收到搜索题库的请求,参数是:{}",req);
        if (req.getCid()==null ) {
            return R.error("请先选择题库");
        }
        if (  StringUtils.isBlank(req.getKeywords())) {
            return R.error("请输入关键词");
        }
        PageResult<QuestionsVo> page = questionInfoService.findQuestionPageInfoByKeywords(req);
        return R.success(page);
    }
    @PostMapping("/today")
    public R getTodayQuestionInfo(BaseReq req){
        logger.info("收到今天获取每日一答的请求:{}",req);
        TodayQuestionInfo tqi  = questionInfoService.findTodayQuestionInfo(req.getUid());
        return R.success(tqi);
    }


    @PostMapping("/today/answer")
    public R userAnswerTodayQuestion(UserAnswerReq req){
        logger.info("收到用户每日一题答题的请求:{}",req);
        if (StringUtils.isBlank(req.getUserAnswer())) {
            return R.error(ErrorCodeEnum.GL9999100);
        }
        String integral =  questionInfoService.saveUserTodayQuestionAnswer(req );
        return R.success(integral);
    }
}
