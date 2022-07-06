package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.api.req.ExportInfoReq;
import com.siival.bot.modules.api.resp.ExportInfo;
import com.siival.bot.modules.bsc.constant.SystemSettingConstant;
import com.siival.bot.modules.bsc.service.ExamInfoService;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.resp.R;
import com.siival.bot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExportInfoController
 * 
 * @Date 2022-03-04 10:32
 */
@RestController
@RequestMapping("/weixin/api/export")
public class ExportInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionInfoService questionInfoService ;
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysSettingService sysSettingService;

    @PostMapping("/info")
    public R findExportInfo(ExportInfoReq req ){
        logger.info("查询导出请求数据:{}",req);
        ExportInfo info = questionInfoService.findQuestionExportInfo(req);
        return R.success(info);
    }
    @PostMapping("/")
    public R exportQuestion(ExportInfoReq req) {
        logger.info("收到导出题库的请求:{}",req);
        if (StringUtils.isBlank(req.getEmail())) {
            return R.error("请输入邮箱地址");
        }

        ExportInfo info = questionInfoService.findQuestionExportInfo(req);
        logger.info("用户导出题库的请求:{}",info);
        if (info.getCount() > info.getIntegral()) {
            return R.success("您的积分不足，获取更多积分再试试吧");
        }

        Integer integral = questionInfoService.exportQuestionInfo(req,info);
        return R.success(integral);
    }

    @PostMapping("/exam")
    public R exportExam(ExportInfoReq req) {
        logger.info("收到导出试卷的请求:{}",req);
        if (StringUtils.isBlank(req.getEmail())) {
            return R.error("请输入邮箱地址");
        }
        Integer needIntegral = sysSettingService.findSystemSettingValueToInteger(SystemSettingConstant.EXAM_INTEGRAL);
        Integer integral = userInfoService.findUserIntegral(req.getUid());
        if (integral<needIntegral) {
            return R.success("您的积分不足，获取更多积分再试试吧");
        }
        Integer afterIntegral = examInfoService.exportExamInfo(req, needIntegral);
        return R.success(afterIntegral);
    }
}
