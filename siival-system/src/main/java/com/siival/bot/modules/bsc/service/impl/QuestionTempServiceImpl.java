package com.siival.bot.modules.bsc.service.impl;

import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.modules.api.resp.SelectListInfo;
import com.siival.bot.modules.bsc.domain.QuestionInfo;
import com.siival.bot.modules.bsc.domain.QuestionTemp;
import com.siival.bot.modules.bsc.repository.QuestionInfoRepository;
import com.siival.bot.modules.bsc.repository.QuestionTempRepository;
import com.siival.bot.modules.bsc.service.QuestionTempService;
import com.siival.bot.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionTempServiceImpl
 * 
 * @Date 2022-03-09 17:06
 */
@Service
public class QuestionTempServiceImpl implements QuestionTempService  {

    private static String[] options = {"A","B","C","D","E","F","G"};

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionTempRepository questionTempRepository;
    @Autowired
    private QuestionInfoRepository questionInfoRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveHandlerQuestionTemp() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC,"id") );
        int page = 0;
        int size = 10;
        Integer status = 1;
        Pageable pageable =   PageRequest.of(page, size, sort  );
        Page<QuestionTemp> pageList = questionTempRepository.findTempQuestion(status, pageable);
        while (!pageList.getContent().isEmpty()) {
            List<QuestionTemp> list = pageList.getContent();
            for (QuestionTemp temp:list) {
                logger.info("开始处理temp问题id:{}",temp.getId());
                QuestionInfo questionInfo = buildQuestionByTemp(temp);
                questionInfoRepository.save(questionInfo);
                //2时已处理
                questionTempRepository.updateQuestionTempStatus(2,temp.getId());
                //System.out.println(questionInfo);
            }


            pageable =   PageRequest.of(page, size, sort  );
            pageList = questionTempRepository.findTempQuestion(status, pageable);
        }

    }

    @Override
    public void handlerQuestionTemp() {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"id") );
        int page = 0;
        int size = 10;
        Integer status = 1;
        Timestamp createTime  = TimeUtils.getTodayStart();
        Pageable pageable =   PageRequest.of(page, size, sort  );
        Page<QuestionTemp> pageList = questionTempRepository.findTempQuestionBuildOptions(status,createTime, pageable);
        long total = pageList.getTotalElements();
        System.out.println("总共多少条数据："+total);
        int a = 0;
        while (!pageList.getContent().isEmpty()) {
            List<QuestionTemp> list = pageList.getContent();
            for (QuestionTemp temp:list) {
                logger.info("开始处理:{}",temp.getId());
                String solution = temp.getSolution();
                if (solution==null) {
                    continue;
                }
                String answer = solution.trim();
                if (answer.matches("\\w+")) {
                    System.out.println("答案全部是选项:"+answer+",id:"+temp.getId());
                    String question = temp.getQuestion();
                    if (question.contains("。")) {
                        String options = question.substring(question.lastIndexOf("。"));
                        if (!options.contains("A")) {
                            continue;
                        }
                        options = options.substring(options.indexOf("A"));
                        System.out.println("原来options="+options);
                        question = question.replace(options,"");
                        options = buildOptions(options);
                        System.out.println("options====="+options);
                        temp.setQuestion(question);
                        temp.setOptions(options);
                        temp.setUpdateTime(TimeUtils.getCurrentTimestamp());
                        questionTempRepository.save(temp);
                        a++;
                    }else{
                        System.out.println("id不包含可格式化字段手工处理"+temp.getId());
                    }

                }
            }
            page++;
            pageable =   PageRequest.of(page, size, sort  );
            pageList = questionTempRepository.findTempQuestionBuildOptions(status,createTime, pageable);
        }
        System.out.println("a="+a);
    }

    private QuestionInfo buildQuestionByTemp(QuestionTemp temp) {
        QuestionInfo qi = new QuestionInfo();
        qi.setAnalysis(temp.getAnswerDesc()  );
        qi.setType(1);
        String answer = temp.getSolution().trim();
        qi.setQuestion((answer.length()>1?"[多选]":"[单选]")+temp.getQuestion());
        qi.setMultiply(answer.length()>1?1:0);
        qi.setRightAnswer(buildAnswer(answer));
        qi.setPid(temp.getPid());
        qi.setStatus(1);
        qi.setCreateTime(TimeUtils.getCurrentTimestamp());
        qi.setSelectList(buildOptions(temp.getSolution(),temp.getOptions()));
        return qi;
    }

    private static String buildOptions(String optionStr) {
        StringBuilder sb = new StringBuilder();
        String [] arr = optionStr.split("[A-G]");
        int index = 0;
        for (int i=0;i<arr.length;i++) {
            String option=arr[i];
            if (option.trim().equals("")) {
                continue;
            }
            sb.append(options[index++] + " " + option.trim()).append("#@#");

        }
        String str = sb.toString();
        return str.substring(0,str.lastIndexOf("#@#"));
    }

    private List<SelectListInfo> buildOptions(String solution, String optionStr) {

        String [] arr = optionStr.split("[A-Z]");
        List<SelectListInfo> list = new ArrayList<>();
        int index = 0;

        for (int i=0;i<arr.length;i++) {

            String option=arr[i];
            if (option.trim().equals("")) {
                continue;
            }

            SelectListInfo sli = new SelectListInfo();
            sli.setTitle(options[index] + option.trim());
            /*
            if (solution.contains(options[index])) {
                sli.setRight(1);
            }
            */
            list.add(sli);
            index++;
        }

        return list;
    }

    public static String buildAnswer(String answer){
        StringBuilder sb = new StringBuilder();
        answer = answer.replace("(","").replace(")","");
        for (int i=0;i<answer.length();i++) {
            sb.append(answer.charAt(i)+",");
        }
        String str = sb.toString();
        return str.substring(0,sb.lastIndexOf(","));
    }

    public static void main(String[] args) {
        String o = "A．审判监督程序是纠正生效裁判错误的法定程序 B．审判监督程序是诉讼的独立审级 C．审判监督程序是案件审理的必经程序 D．审判监督程序是第二审程序";
    }
}
