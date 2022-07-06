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

import com.siival.bot.enums.IntegralCountEnum;
import com.siival.bot.enums.IntegralTypeEnum;
import com.siival.bot.modules.api.constant.UserConstant;
import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.UserSignInfo;
import com.siival.bot.modules.bsc.domain.UserInfo;
import com.siival.bot.modules.bsc.domain.UserIntegralLog;
import com.siival.bot.modules.bsc.domain.UserSignLog;
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.repository.UserIntegralLogRepository;
import com.siival.bot.modules.bsc.repository.UserSignLogRepository;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserInfoRepository;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.modules.bsc.service.dto.UserInfoDto;
import com.siival.bot.modules.bsc.service.dto.UserInfoQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-02-21
**/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private   UserInfoRepository userInfoRepository;
    @Autowired
    private   UserInfoMapper userInfoMapper;
    @Autowired
    private   UserIntegralLogRepository userIntegralLogRepository;
    @Autowired
    private   UserSignLogRepository userSignLogRepository;
    @Autowired
    private   RedisUtils redisUtils;


    @Override
    public Map<String,Object> queryAll(UserInfoQueryCriteria criteria, Pageable pageable){
        Page<UserInfo> page = userInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userInfoMapper::toDto));
    }

    @Override
    public Map<String,Object> queryAllByParams(UserInfoQueryCriteria criteria, Pageable pageable){

        return null;
//        return PageUtil.toPage(page.map(userInfoMapper::toDto));
    }

    @Override
    public List<UserInfoDto> queryAll(UserInfoQueryCriteria criteria){
        return userInfoMapper.toDto(userInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserInfoDto findById(Integer id) {
        UserInfo userInfo = userInfoRepository.findById(id).orElseGet(UserInfo::new);
        ValidationUtil.isNull(userInfo.getId(),"UserInfo","id",id);
        return userInfoMapper.toDto(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfoDto create(UserInfo resources) {
        return userInfoMapper.toDto(userInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInfo resources) {
        UserInfo userInfo = userInfoRepository.findById(resources.getId()).orElseGet(UserInfo::new);
        ValidationUtil.isNull( userInfo.getId(),"UserInfo","id",resources.getId());
        userInfo.copy(resources);
        userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserInfoDto userInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", userInfo.getUser().getId());
            map.put("用户昵称", userInfo.getUser().getNickName());
            map.put("积分", userInfo.getIntegral());
            map.put("累计签到天数", userInfo.getSignTotalCount());
            map.put("连续签到天数", userInfo.getSignContinuousCount());
            map.put("创建时间", userInfo.getCreateTime());
            map.put("更新时间", userInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public UserSignInfo findUserSignInfo(BaseReq req) {
        logger.info("收到查询签到需求:{}",req.getUid());
        UserInfo ui = userInfoRepository.findFirstByUserId(req.getUid());
        UserSignInfo usi = new UserSignInfo();
        if (ui==null) {
            usi.setContinuousSign(0);
            usi.setTotalSign(0);

        }else{
            usi.setContinuousSign(ui.getSignContinuousCount());
            usi.setTotalSign(ui.getSignTotalCount());
        }
        usi.setToday(TimeUtils.getTodayStr());
        usi.setStartTime(TimeUtils.getMonthStartStr());


        Date startDay  = null;
        try {
            startDay = TimeUtils.getMonthStartDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String,Integer> map = null;
        List<UserSignLog> list = userSignLogRepository.findUserSignLogInfo(req.getUid(), startDay);
        if (list==null || list.isEmpty()){
            map = new HashMap<>();
        }else {
            map = list.stream().collect(Collectors.toMap(
                    s -> {
                        String createTime = s.getCreateTime().toString();
                        return createTime.substring(0, createTime.indexOf(" "));
                    }, UserSignLog::getIntegral, (k1, k2) -> k1));
        }
        usi.setDayIntegral(map);
        return usi;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveUserSignInfo(BaseReq req) {
        logger.info("收到签到:{}",req.getUid());
        String alreadyKey = UserConstant.USER_ALREADY_SIGN_KEY + req.getUid();
        boolean alreadySign = redisUtils.hasKey(alreadyKey);
        logger.info("uid:{},今天是否签到:{}",req.getUid(),alreadySign);
        if (alreadySign) {
            return "亲，今天已签到喔！";
        }
        UserInfo ui = userInfoRepository.findFirstByUserId(req.getUid());
        Integer integral = 0;
        Integer continuousCount = 0;
        if (ui==null) {
            ui = ui = buildUserInfo(req.getUid());
        }else{
            ui.setUpdateTime(TimeUtils.getCurrentTimestamp());
        }
        continuousCount = ui.getSignContinuousCount();
        String continuousKey = UserConstant.USER_CONTINUOUS_ALREADY_SIGN_KEY + req.getUid();
        boolean continueSign = redisUtils.hasKey(continuousKey);
        if (continueSign) {
            //连续签到
            logger.info("uid:{},连续签到,天数为: ",req.getUid(),continuousCount);
            integral = IntegralCountEnum.getIntegralByCount( continuousCount + 1);
            ui.setSignContinuousCount(continuousCount +1 );
        }else{
            //断签 获得积分直接为1
            logger.info("uid:{},连续断签",req.getUid());
            ui.setSignContinuousCount(1);
            integral = IntegralCountEnum.getIntegralByCount(1);
        }
        Integer before = ui.getIntegral();
        Integer after = before + integral;
        ui.setIntegral(after);
        ui.setSignTotalCount(ui.getSignTotalCount()+1);
        UserIntegralLog uil = buildUserIntegralLog(before, after,integral,req.getUid(),"签到", IntegralTypeEnum.ADD_INTEGRAL.getType());
        userIntegralLogRepository.save(uil);
        UserSignLog usl = buildUserSignLog(req.getUid(), integral );
        userSignLogRepository.save(usl);

        userInfoRepository.save(ui);
        //不报错, set redis
        redisUtils.set(alreadyKey, "1", TimeUtils.diffSecondNowToDate(TimeUtils.getTodayEndTime()));
        LocalDateTime tomorrow = TimeUtils.getAfterDayEndTime(1L);
        redisUtils.set(continuousKey,"1", TimeUtils.diffSecondNowToDate( tomorrow ) );
        return integral.toString()+","+ui.getSignTotalCount()+","+ui.getSignContinuousCount();
    }

    public UserSignLog buildUserSignLog(Integer uid, Integer integral) {
        UserSignLog usi = new UserSignLog();
        usi.setIntegral(integral);
        WxUser user = new WxUser();
        user.setId(uid);
        usi.setUser(user);
        usi.setCreateTime(TimeUtils.getCurrentTimestamp());
        return usi;
    }

    @Override
    public Integer findUserIntegral(Integer uid) {
        UserInfo ui = userInfoRepository.findFirstByUserId(uid);
        return ui==null?0:ui.getIntegral();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserIntegralLog(ModifyUserIntegralReq req) {
        UserInfo ui = userInfoRepository.findFirstByUserId(req.getId());
        Integer integral = 0;
        Integer status = req.getStatus();
        if (ui==null) {
            //原来ui不存在，所以此时积分扣减没意义,
            ui = buildUserInfo(req.getId());
        }else{
            //原始积分
            integral = ui.getIntegral();
            ui.setUpdateTime(TimeUtils.getCurrentTimestamp());
        }
        Integer after = 0;
        Integer type = null;
        if (IntegralTypeEnum.ADD_INTEGRAL.getType().equals(status)) {
            //增加积分
            after = integral + req.getIntegral();
            type = IntegralTypeEnum.ADD_INTEGRAL.getType();
        }else{
            after = integral - req.getIntegral();
            type = IntegralTypeEnum.REDUCE_INTEGRAL.getType();
        }
        after = after<0?0:after;
        ui.setIntegral(after);

        UserIntegralLog uil = buildUserIntegralLog(integral, after,req.getIntegral(),req.getId(),req.getRemark(), type);
        userIntegralLogRepository.save(uil);
        userInfoRepository.save(ui);
    }

    private UserInfo buildUserInfo(Integer uid) {
        UserInfo ui =  new UserInfo();
        ui.setCreateTime( TimeUtils.getCurrentTimestamp() );
        ui.setIntegral(0);
        ui.setSignTotalCount(0);
        WxUser user = new WxUser();
        user.setId(uid);
        ui.setUser(user);
        ui.setSignContinuousCount(0);
        return ui;
    }

    public UserIntegralLog buildUserIntegralLog(Integer before, Integer after, Integer integral, Integer uid, String s, Integer type) {
        UserIntegralLog uil = new UserIntegralLog();
        uil.setBeforeCount(before);
        uil.setAfterCount(after);
        uil.setIntegral(integral);
        WxUser user = new WxUser();
        user.setId(uid);
        uil.setUser(user);
        uil.setRemark(s);
        uil.setType(type);
        uil.setCreateTime(TimeUtils.getCurrentTimestamp());
        return uil;
    }
}