package com.siival.bot;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.siival.bot.modules.api.req.QuestionInfoListReq;
import com.siival.bot.modules.api.req.SearchQuestionInfoReq;
import com.siival.bot.modules.api.resp.CategoryInfo;
import com.siival.bot.modules.api.resp.ExamScoreRankVo;
import com.siival.bot.modules.api.resp.QuestionsVo;
import com.siival.bot.modules.bsc.domain.UserSignLog;
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.repository.UserExamScoreRepository;
import com.siival.bot.modules.bsc.repository.UserSignLogRepository;
import com.siival.bot.modules.bsc.repository.WxUserRepository;
import com.siival.bot.modules.bsc.service.QuestionInfoService;
import com.siival.bot.modules.bsc.service.QuestionMenuService;
import com.siival.bot.modules.bsc.service.WxUserService;
import com.siival.bot.resp.PageResult;
import com.siival.bot.utils.TimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName JpaRepositoryTest
 * 
 * @Date 2022/1/17 9:58
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaRepositoryTest {
    @Autowired
    public WxUserRepository userRepository;
    @Autowired
    private WxUserService userService;
    @Autowired
    private QuestionMenuService questionMenuService;
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private UserExamScoreRepository userExamScoreRepository;
    @Autowired
    private UserSignLogRepository userSignLogRepository;

    @Test
    public void testFindUser() {
        WxUser dto =  userRepository.findFirstByOpenid("");
        System.out.println(dto);
    }

    @Test
    public void testSaveUser() {
        WxMaJscode2SessionResult session = new WxMaJscode2SessionResult();
        session.setSessionKey("123");
        session.setOpenid("4561");
        session.setUnionid("7891");
        Integer id = userService.saveOrFindUserIdBySession(session, null);
        System.out.println(id);
    }
    @Test
    public void testFindMenu() {
        List<CategoryInfo> list = questionMenuService.getEnableMenuInfo(0);
        System.out.println(list);
    }

    @Test
    public void testFindQuestion() {
//        List<QuestionsVo> list = questionInfoService.findQuestionInfoVosByPid(111);
//        System.out.println(list);
        SearchQuestionInfoReq req = new SearchQuestionInfoReq();
        req.setCid(3);
        req.setKeywords("下面");
        req.setPage(2);
        req.setSize(3);
        PageResult<QuestionsVo> result = questionInfoService.findQuestionPageInfoByKeywords(req);
        System.out.println(result);
    }

    @Test
    public void testFindFavorite() {
        QuestionInfoListReq req = new QuestionInfoListReq();
        req.setCid(93);
        req.setUid(3);
        List<QuestionsVo> list = questionInfoService.findUserFavoriteQuestionInfos(req);
        System.out.println(list);
    }

    @Test
    public void testUserExamScore() {
        Integer pid = 3;
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"score"),new Sort.Order(Sort.Direction.ASC, "createTime"));
        Pageable pageable =   PageRequest.of(0, 2,sort  );

        List<Object[]> list = userExamScoreRepository.selectExamScoreRankVo(pid,pageable);
        for (Object[] arr: list) {
            System.out.println(Arrays.toString(arr));
        }

    }

    @Test
    public void testUserSign() {
        Date startDay  = null;
        try {
            startDay = TimeUtils.getMonthStartDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<UserSignLog> list = userSignLogRepository.findUserSignLogInfo(3, startDay);

        Map<String,Integer> map = list.stream().collect(Collectors.toMap(
                s-> {
                    String createTime = s.getCreateTime().toString();
                    return createTime.substring(0,createTime.indexOf(" "));
                } ,UserSignLog::getIntegral , (k1, k2) ->k1));
        System.out.println(map);
    }
}
