/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.siival.bot.modules.bsc.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.siival.bot.config.FileProperties;
import com.siival.bot.domain.vo.EmailVo;
import com.siival.bot.enums.*;
import com.siival.bot.modules.api.config.PdfExportConfig;
import com.siival.bot.modules.api.constant.CommonConstant;
import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.modules.api.req.*;
import com.siival.bot.modules.api.resp.ExportInfo;
import com.siival.bot.modules.api.resp.QuestionsVo;
import com.siival.bot.modules.api.resp.TodayQuestionInfo;
import com.siival.bot.modules.bsc.constant.SystemSettingConstant;
import com.siival.bot.modules.bsc.domain.*;
import com.siival.bot.modules.bsc.repository.*;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.modules.pdf.PdfConstant;
import com.siival.bot.modules.pdf.PdfExport;
import com.siival.bot.resp.PageResult;
import com.siival.bot.service.EmailService;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.dto.QuestionInfoDto;
import com.siival.bot.modules.bsc.service.dto.QuestionInfoQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.QuestionInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.*;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-02-12
**/
@Service
public class QuestionInfoServiceImpl implements QuestionInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserWrongQuestionRepository userWrongQuestionRepository;
    @Autowired
    private UserFavoriteQuestionRepository userFavoriteQuestionRepository;
    @Autowired
    private QuestionInfoRepository questionInfoRepository;
    @Autowired
	private QuestionInfoMapper questionInfoMapper;
    @Autowired
	private UserCorrectQuestionRepository userCorrectQuestionRepository;
    @Autowired
	private UserInfoService userInfoService;
    @Autowired
	private PdfExportConfig pdfExportConfig;
    @Autowired
	private QuestionMenuRepository questionMenuRepository;
    @Autowired
	private FileProperties fileProperties;
    @Autowired
	private UserIntegralLogRepository userIntegralLogRepository;
    @Autowired
	private UserExportLogRepository userExportLogRepository;
    @Autowired
	private UserInfoRepository userInfoRepository;

    @Autowired
	private EmailService emailService;

    @Autowired
	private RedisUtils redisUtils;

    @Autowired
	private QuestionTodayRepository questionTodayRepository;

    @Autowired
	private QuestionTodayAnswerRepository questionTodayAnswerRepository;

    @Autowired
	private SysSettingService sysSettingService;
    @Override
    public Map<String,Object> queryAll(QuestionInfoQueryCriteria criteria, Pageable pageable){
        Page<QuestionInfo> page = questionInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(questionInfoMapper::toDto));
    }

    @Override
    public List<QuestionInfoDto> queryAll(QuestionInfoQueryCriteria criteria){
        return questionInfoMapper.toDto(questionInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QuestionInfoDto findById(Integer id) {
        QuestionInfo questionInfo = questionInfoRepository.findById(id).orElseGet(QuestionInfo::new);
        ValidationUtil.isNull(questionInfo.getId(),"QuestionInfo","id",id);
        return questionInfoMapper.toDto(questionInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionInfoDto create(QuestionInfo resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        buildQuestion(resources);
        return questionInfoMapper.toDto(questionInfoRepository.save(resources));
    }

    private void buildQuestion(QuestionInfo resources) {
        if (QuestionsInfoTypeEnum.OPTIONS.getCode().equals(resources.getType())) {
            //选择题，格式化
            String answer = resources.getRightAnswer();
            if(answer.contains("，")){
                answer = answer.replace("，",",");
            }
            resources.setRightAnswer(answer);
            String question = resources.getQuestion();
            if (!question.startsWith("[单选") && !question.startsWith("【单选")
            && !question.startsWith("[多选") && !question.startsWith("【多选")) {
                String type = answer.length()>1?"[多选]":"[单选]";
                question = type + question;
            }
            resources.setQuestion(question);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuestionInfo resources) {
        QuestionInfo questionInfo = questionInfoRepository.findById(resources.getId()).orElseGet(QuestionInfo::new);
        ValidationUtil.isNull( questionInfo.getId(),"QuestionInfo","id",resources.getId());
        questionInfo.copy(resources);
        questionInfo.setUpdateTime(TimeUtils.getCurrentTimestamp());
        buildQuestion(questionInfo);
        questionInfoRepository.save(questionInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            questionInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QuestionInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuestionInfoDto questionInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("问题类型", questionInfo.getType());
            map.put("是否多选", questionInfo.getMultiply());
            map.put("上级分类", questionInfo.getPid());
            map.put("创建时间", questionInfo.getCreateTime());
            map.put("更新时间", questionInfo.getUpdateTime());
            map.put("答案解析", questionInfo.getAnalysis());
            map.put("是否启用", questionInfo.getStatus());
            map.put("答案", questionInfo.getRightAnswer());
            map.put("问题", questionInfo.getQuestion());
            map.put("选项列表", questionInfo.getSelectList());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<QuestionsVo> findQuestionInfoVosByPid(Integer pid) {
        List<QuestionInfo> list =   questionInfoRepository.findByStatusAndPidAndTypeOrderByIdAsc(CommonStatusEnum.ENABLE.getKey(), pid,
                QuestionsInfoTypeEnum.OPTIONS.getCode());
        return list==null?null:list.stream().map(s->{
            QuestionsVo vo = getQuestionsVo(s);
            return vo;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveUserWrongQuestion(QuestionReq req) throws Exception {
        UserWrongQuestion question = userWrongQuestionRepository.findFirstByPidAndUidAndQuestionId(req.getCid(), req.getUid(), req.getId());
        if ( req.getType()!=null && req.getType().equals(1)) {
            //添加错题,
            if (question!=null) {
                //已经添加收藏
                return;
            }
            question = new UserWrongQuestion();
            question.setQuestionId(req.getId());
            question.setPid(req.getCid());
            question.setUid(req.getUid());
            userWrongQuestionRepository.save(question);
        }else{
            //移除错题
            if (question==null) {
                return;
            }
            userWrongQuestionRepository.deleteById(question.getId());
        }
    }

    @Transactional
    @Override
    public void saveUserFavoriteQuestion(QuestionReq req) throws Exception {
        UserFavoriteQuestion question = userFavoriteQuestionRepository.findFirstByPidAndUidAndQuestionId(req.getCid(), req.getUid(), req.getId());
        if ( req.getType()!=null && req.getType().equals(1)) {
            //添加收藏,

            if (question!=null) {
                //已经添加收藏
                return;
            }
            question = new UserFavoriteQuestion();
            question.setQuestionId(req.getId());
            question.setPid(req.getCid());
            question.setUid(req.getUid());
            userFavoriteQuestionRepository.save(question);
        }else {
            //移除收藏
            if (question==null) {
                return;
            }
            userFavoriteQuestionRepository.deleteById(question.getId());
        }
    }

    @Override
    public int findUserFavoriteQuestion(QuestionReq req) {
        UserFavoriteQuestion question = userFavoriteQuestionRepository.findFirstByPidAndUidAndQuestionId(req.getCid(), req.getUid(), req.getId());
        return question!=null?1:0;
    }

    @Override
    public long findUserFavoriteQuestionCount(QuestionReq req) {
        return userFavoriteQuestionRepository.countByPidAndUid(req.getCid(), req.getUid());
    }

    @Override
    public long findUserWrongQuestionCount(QuestionReq req) {
        return userWrongQuestionRepository.countByPidAndUid(req.getCid(), req.getUid());
    }

    @Override
    public List<QuestionsVo> findUserFavoriteQuestionInfos(QuestionInfoListReq req) {
        List<QuestionInfo> list = questionInfoRepository.findUserFavoriteQuestion(req.getCid(), req.getUid());
        return list==null?null:list.stream().map(s->{
            QuestionsVo vo = getQuestionsVo(s);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<QuestionsVo> findUserWrongQuestionInfos(QuestionInfoListReq req) {
        List<QuestionInfo> list = questionInfoRepository.findUserWrongQuestion(req.getCid(), req.getUid());
        return list==null?null:list.stream().map(s->{
            QuestionsVo vo = getQuestionsVo(s);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public long findQuestionCount(Integer cid) {
        return questionInfoRepository.countByPidAndStatusAndType(cid, CommonStatusEnum.ENABLE.getKey(),QuestionsInfoTypeEnum.OPTIONS.getCode());
    }

    @Override
    public List<QuestionsVo> findShareQuestionByQid(Integer qid) {
        QuestionInfo  s = questionInfoRepository.findById(qid).orElseGet(QuestionInfo::new);;
        QuestionsVo vo = getQuestionsVo(s);
        List<QuestionsVo> list = new ArrayList<>();
        list.add(vo);
        return list;
    }

    @Override
    public String saveUserQuestionCorrect(QuestionCorrectReq req) {

        UserCorrectQuestion ucq = new UserCorrectQuestion();
        WxUser user = new WxUser();
        user.setId(req.getUid());
        ucq.setUser(user);
        ucq.setPid(req.getCid());
        QuestionInfo qi = new QuestionInfo();
        qi.setId(req.getQid());
        ucq.setQuestion (qi);
        ucq.setReason(req.getReason());
        ucq.setCreateTime(TimeUtils.getCurrentTimestamp());
        ucq.setStatus(QuestionCorrectEnum.WAITING_CHECK.getCode());
        userCorrectQuestionRepository.save(ucq);
        return "提交成功，审核通过后您将获得积分奖励喔!";
    }



    @Override
    public PageResult<QuestionsVo> findQuestionPageInfoByKeywords(SearchQuestionInfoReq req) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id") );
        Pageable pageable =   PageRequest.of(req.getPage(), req.getSize(), sort  );

        PageResult<QuestionsVo> result = new PageResult<>(req.getPage(), req.getSize());
        Page<QuestionInfo> page =  questionInfoRepository.findByStatusAndPidAndTypeAndQuestionContaining(CommonStatusEnum.ENABLE.getKey(), req.getCid(),
                QuestionsInfoTypeEnum.OPTIONS.getCode(),
                req.getKeywords(), pageable);
        if (page==null || page.isEmpty()) {
            result.setTotalSize(0L);
            result.setTotalPages(0L);
        }else{
            result.setTotalSize(page.getTotalElements());
            result.setTotalPages(page.getTotalPages());
            List<QuestionInfo> list = page.getContent();
            result.setList(list==null?null:list.stream().map(s->{
                QuestionsVo vo = new QuestionsVo();
                vo.setId(s.getId());
                vo.setQuestion(s.getQuestion());
                return vo;
            }).collect(Collectors.toList()));
        }

        return result;
    }

    @Override
    public ExportInfo findQuestionExportInfo(ExportInfoReq req) {
        ExportInfo ei = new ExportInfo();
        ei.setCount(findQuestionCount(req.getCid()));
        ei.setIntegral(userInfoService.findUserIntegral(req.getUid()));
        return ei;
    }

    @Override
    public String  createQuestionPdfFile(Integer pid) {
        //应该是读取文件路径

        QuestionMenu  menu = questionMenuRepository.findById(pid).orElseGet(QuestionMenu::new);
        if (StringUtils.isAllBlank(menu.getName())) {
            return "";
        }
        String title = menu.getName();
        List<QuestionsVo> vos = findQuestionInfoVosByPid(pid);
        if (vos==null || vos.isEmpty()) {
            return "";
        }
        String outputFile = buildPdfName(title);
        File file = new File(outputFile);
        if (file.exists()) {
            logger.info("pid:{},名称:{}的pdf文件已生成",pid,title);
            return outputFile;
        }
        PdfExport.writeFileToPdf(title,pdfExportConfig,vos,outputFile);
        return outputFile;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer exportQuestionInfo(ExportInfoReq req,ExportInfo info ) {
        UserExportLog log = new UserExportLog();
        log.setUid(req.getUid());
        log.setPid(req.getCid());
        log.setCreateTime(TimeUtils.getCurrentTimestamp());
        log.setEmail(req.getEmail());
        log.setExportType(ExportTypeEnum.QUESTIONS.getCode());
        Integer integral = Integer.valueOf(String.valueOf(info.getCount()));
        log.setIntegral(integral);
        log.setStatus(ExportStatusEnum.WAITING.getStatus());
        userExportLogRepository.save(log);

        Integer after = info.getIntegral() - integral;
        logger.info("用户题库导出后所剩积分为:{}",after);
        UserIntegralLog userIntegralLog = userInfoService.buildUserIntegralLog(info.getIntegral(),
                after, integral, req.getUid(), "导出题库", IntegralTypeEnum.REDUCE_INTEGRAL.getType());
        userIntegralLogRepository.save(userIntegralLog);
        userInfoRepository.updateUserInfoIntegral(after, req.getUid());
        return after;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerExportQuestion() {
        logger.info("开始处理导出题库的定时任务");
        List<UserExportLog> list = userExportLogRepository.findByStatusAndExportType(ExportStatusEnum.WAITING.getStatus(), ExportTypeEnum.QUESTIONS.getCode());
        if (list==null || list.isEmpty()) {
            return;
        }
        for (UserExportLog log : list) {
            logger.info("开始处理导出题库log为：{}",log);
            String outputFile = createQuestionPdfFile(log.getPid());
            EmailVo vo = new EmailVo();
            vo.setSubject("导出题库");
            vo.setContent("题库内容见附件") ;
            List<String> tos = new ArrayList<>();
            tos.add(log.getEmail());
            vo.setTos(tos);
            vo.setFileName(outputFile);
            try {
                emailService.send(vo);
                log.setStatus(ExportStatusEnum.SUCCESS.getStatus());
                userExportLogRepository.save(log);
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public TodayQuestionInfo findTodayQuestionInfo(Integer uid) {
        Object str = redisUtils.get(RedisKeyConstant.TODAY_QUESTION_KEY);
        if (str==null) {
            return null;
        }
        TodayQuestionInfo tqi = JSONUtil.toBean(str.toString(), TodayQuestionInfo.class);
        if (tqi==null) {
            return null;
        }
//        tqi.setQuestions(findShareQuestionByQid(tqi.getQid()));
        String alreadyKey = RedisKeyConstant.TODAY_USER_ALREADY_ANSWER_QUESTION + uid;
        Object obj = redisUtils.get(alreadyKey);
        tqi.setAlreadyAnswer(obj!=null);
        Integer answerIntegral = 0;
        try {
            answerIntegral = Integer.valueOf(obj.toString());
        }catch (Exception e){}
        tqi.setAnswerIntegral(answerIntegral);
        return tqi;
    }

    @Override
    public void refreshTodayQuestion() {
        logger.info("开始刷新今天的每一一题");
        TodayQuestionInfo tqi = new TodayQuestionInfo();
        Object[] count = questionInfoRepository.findFirstMinAndMaxByType(QuestionsInfoTypeEnum.OPTIONS.getCode());
        count =(Object[] ) count[0];
        logger.info("查询题目总数为:{},min:{},max:{}",count[2],count[0],count[1]);
        Integer min = Integer.valueOf(count[0].toString());
        Integer max = Integer.valueOf(count[1].toString());
        tqi.setTotalCount(Long.valueOf(count[2].toString()));

        Integer integral = sysSettingService.findSystemSettingValueToInteger(SystemSettingConstant.TODAY_QUESTION_INTEGRAL);
        tqi.setIntegral(integral);
        Integer id = RandomUtil.randomInt(min,max);
        logger.info("随机id值为:{}",id);
        QuestionInfo info = null;
        if ((max-id) <10) {
            //距离最大值再20以内，向下找一个问题
            info = questionInfoRepository.findFirstByIdLessThanEqualAndType (id, QuestionsInfoTypeEnum.OPTIONS.getCode());
        }else{
            //向上找1个问题
            info = questionInfoRepository.findFirstByIdGreaterThanEqualAndType (id, QuestionsInfoTypeEnum.OPTIONS.getCode());
        }
        if (info!=null) {
            logger.info("获取到了最近的id:{}",info.getId());
            tqi.setQid(info.getId());
            List<QuestionsVo> vos = new ArrayList<>();
            vos.add(getQuestionsVo(info));
            tqi.setQuestions(vos);
        }
        redisUtils.set(RedisKeyConstant.TODAY_QUESTION_KEY, JSONUtil.toJsonStr(tqi), TimeUtils.diffSecondNowToDate(TimeUtils.getTodayEndTime()));
        QuestionToday todayQuestion = new QuestionToday();
        todayQuestion.setCreateTime(TimeUtils.getCurrentTimestamp());
        todayQuestion.setIntegral(integral);
        todayQuestion.setStatus(CommonStatusEnum.ENABLE.getKey());
        todayQuestion.setQuestion(info);
        questionTodayRepository.save(todayQuestion);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveUserTodayQuestionAnswer(UserAnswerReq req) {
        String alreadyKey = RedisKeyConstant.TODAY_USER_ALREADY_ANSWER_QUESTION + req.getUid();
        boolean alreadyAnswer = redisUtils.hasKey(alreadyKey) ;
        if (alreadyAnswer) {
            return "您今天已答过题目了";
        }
        Object obj = redisUtils.get(RedisKeyConstant.TODAY_QUESTION_KEY);
        if (obj==null) {
            return "今天题目还未刷新，无法作答";
        }
        TodayQuestionInfo tqi = JSONUtil.toBean(obj.toString(), TodayQuestionInfo.class);
        if (tqi.getQuestions()==null || tqi.getQuestions().isEmpty()) {
            return "今天题目还未刷新，无法作答";
        }
        QuestionsVo vo = tqi.getQuestions().get(0);

        String [] arr = req.getUserAnswer().split(",");
        Set<String> answers = new HashSet<>();
        for (String str : arr) {
            int index = Integer.valueOf(str);
            if (index>CommonConstant.OPTIONS_ARRAY.length) {
                break;
            }
            answers.add(CommonConstant.OPTIONS_ARRAY[index]);
        }
        List<String> rightAnser =  Arrays.stream(vo.getRightAnswer().split(","))

                .collect(Collectors.toList());
        boolean answerRight = true;
        if (answers.size()!=rightAnser.size()) {
            answerRight = false;
        }else{
            for (String ans : rightAnser) {
                if (!answers.contains(ans)) {
                    answerRight = false;
                    break;
                }
            }
        }
        Integer answerIntegral = 0;
        Integer status = 0;
        if (answerRight) {
            answerIntegral = tqi.getIntegral();
            //回答正确增加积分
            addUserIntegral(answerIntegral, req.getUid(), "每日一题");
            status = 1;
        }
        redisUtils.set(alreadyKey,answerIntegral.toString(), TimeUtils.diffSecondNowToDate(TimeUtils.getTodayEndTime()));
        buildQuestionTodayAnswer(answerIntegral, req.getUid(), answers.toString(), tqi.getQid(), status);
        return answerIntegral.toString();
    }

    private void addUserIntegral(Integer answerIntegral, Integer uid, String remark) {
        ModifyUserIntegralReq req = new ModifyUserIntegralReq();
        req.setIntegral(answerIntegral);
        req.setId(uid);
        req.setRemark(remark);
        req.setStatus(IntegralTypeEnum.ADD_INTEGRAL.getType());
        userInfoService.saveUserIntegralLog(req);
    }

    private void buildQuestionTodayAnswer(Integer answerIntegral, Integer uid, String userAnswer, Integer qid,Integer status) {
        QuestionTodayAnswer qta = new QuestionTodayAnswer();
        QuestionInfo q = new QuestionInfo();
        q.setId(qid);
        qta.setQuestion(q);
        WxUser user = new WxUser();
        user.setId(uid);
        qta.setUser(user);
        qta.setIntegral(answerIntegral);
        qta.setCreateTime(TimeUtils.getCurrentTimestamp());
        qta.setUserAnswer(userAnswer);
        qta.setStatus(status);
        questionTodayAnswerRepository.save(qta);
    }


    private String buildPdfName(String title) {
        String folder = fileProperties.getPath().getPath() + File.separator + PdfConstant.DEFAULT_FOLDER + File.separator;
        String outputFile = folder + title + ".pdf";
        return outputFile;
    }

    private QuestionsVo getQuestionsVo(QuestionInfo s) {
        QuestionsVo vo = new QuestionsVo();
        vo.setQuestion(s.getQuestion());
        vo.setAnalysis(s.getAnalysis());
        vo.setId(s.getId());
        vo.setSelectList(s.getSelectList());
        vo.setType(s.getType());
        vo.setMultiply(s.getMultiply());
        vo.setRightAnswer(s.getRightAnswer());
        return vo;
    }


}