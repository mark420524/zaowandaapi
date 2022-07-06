package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.req.ExamScoreReq;
import com.siival.bot.modules.api.req.ExamSubmitReq;
import com.siival.bot.modules.api.req.SearchExamReq;
import com.siival.bot.modules.api.resp.ExamExportInfo;
import com.siival.bot.modules.api.resp.ExamInfoVo;
import com.siival.bot.modules.api.resp.ExamScoreRankVo;
import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.UserExamScoreService;
import com.siival.bot.resp.PageResult;
import com.siival.bot.resp.R;
import com.siival.bot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExamController
 * 
 * @Date 2022-02-21 13:12
 */
@RestController
@RequestMapping("/weixin/api/exam")
public class ExamController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserExamScoreService userExamScoreService;
    @Autowired
    private ExamInfoService examInfoService;


    @PostMapping("/submit")
    public R saveSubmitUserExam(ExamSubmitReq req) {
        try {
            userExamScoreService.saveUserExamScoreInfo(req);
            return R.success(1);
        } catch (Exception e) {
            e.printStackTrace();
            return R.success(0);
        }

    }

    @PostMapping("/rank")
    public R listExamScoreRank(ExamScoreReq req) {
        List<ExamScoreRankVo> list = userExamScoreService.findExamScoreRankVoByPid(req );
        return R.success(list);
    }
    @PostMapping("/search")
    public R searchExamInfo(SearchExamReq req){
        logger.info("收到搜索试卷的请求,参数是:{}",req);
        if (req.getPid()!=null && req.getPid()>0) {
            req.setPid(req.getPid());
        }else{
            req.setPid(null);
        }
        PageResult<ExamInfoVo> page = examInfoService.findExamInfoVoByKeywords(req);
        return R.success(page);
    }
    @PostMapping("/info")
    public R queryExamExportInfo(BaseReq req) {
        if (req.getUid()==null || req.getUid()<0) {
            return R.error("参数错误");
        }
        ExamExportInfo info = examInfoService.findExamExportInfo(req.getUid());
        return R.success(info);
    }

}
