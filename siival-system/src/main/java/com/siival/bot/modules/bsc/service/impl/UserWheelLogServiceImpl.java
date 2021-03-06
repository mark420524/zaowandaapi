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
import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.enums.IntegralTypeEnum;
import com.siival.bot.modules.api.constant.UserConstant;
import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.UserWheelInfo;
import com.siival.bot.modules.api.resp.UserWheelResult;
import com.siival.bot.modules.bsc.domain.UserWheelLog;
import com.siival.bot.modules.bsc.domain.WheelInfo;
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.modules.bsc.service.WheelInfoService;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserWheelLogRepository;
import com.siival.bot.modules.bsc.service.UserWheelLogService;
import com.siival.bot.modules.bsc.service.dto.UserWheelLogDto;
import com.siival.bot.modules.bsc.service.dto.UserWheelLogQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserWheelLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website
* @description ????????????
* @author mark
* @date 2022-04-19
**/
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserWheelLogServiceImpl implements UserWheelLogService {

    private final UserWheelLogRepository userWheelLogRepository;
    private final UserWheelLogMapper userWheelLogMapper;
    private final RedisUtils redisUtils;
    private final WheelInfoService wheelInfoService;
    private final UserInfoService userInfoService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String,Object> queryAll(UserWheelLogQueryCriteria criteria, Pageable pageable){
        Page<UserWheelLog> page = userWheelLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userWheelLogMapper::toDto));
    }

    @Override
    public List<UserWheelLogDto> queryAll(UserWheelLogQueryCriteria criteria){
        return userWheelLogMapper.toDto(userWheelLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserWheelLogDto findById(Integer id) {
        UserWheelLog userWheelLog = userWheelLogRepository.findById(id).orElseGet(UserWheelLog::new);
        ValidationUtil.isNull(userWheelLog.getId(),"UserWheelLog","id",id);
        return userWheelLogMapper.toDto(userWheelLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserWheelLogDto create(UserWheelLog resources) {
        return userWheelLogMapper.toDto(userWheelLogRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserWheelLog resources) {
        UserWheelLog userWheelLog = userWheelLogRepository.findById(resources.getId()).orElseGet(UserWheelLog::new);
        ValidationUtil.isNull( userWheelLog.getId(),"UserWheelLog","id",resources.getId());
        userWheelLog.copy(resources);
        userWheelLogRepository.save(userWheelLog);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userWheelLogRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserWheelLogDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserWheelLogDto userWheelLog : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("??????id", userWheelLog.getUser().getId());
            map.put("??????????????????", userWheelLog.getIntegral());
            map.put("????????????", userWheelLog.getCreateTime());
            map.put("????????????", userWheelLog.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public UserWheelInfo queryUserTodayWheelInfo(BaseReq req) {
        logger.info("?????????????????????????????????uid:{}",req.getUid());
        String alreadyKey = UserConstant.USER_ALREADY_WHEEL_KEY + req.getUid();
        //alreadyWheel ????????????????????????
        Object  alreadyWheel = redisUtils.get(alreadyKey);
        logger.info("uid:{},?????????????????????:{}",req.getUid(),alreadyWheel);
        UserWheelInfo info = new UserWheelInfo();
        info.setAlreadyWheel(alreadyWheel!=null);
        info.setIntegral(Integer.valueOf(alreadyWheel==null?"0":alreadyWheel.toString()));
        info.setLotteryInfo(wheelInfoService.findEnableWheelInfo());
        return info;
    }

    @Override
    public UserWheelResult saveUserWheelLog(BaseReq req) {
        logger.info("?????????????????????????????????uid:{}",req.getUid());
        String alreadyKey = UserConstant.USER_ALREADY_WHEEL_KEY + req.getUid();
        //alreadyWheel ????????????????????????
        UserWheelResult result = new UserWheelResult();
        boolean  alreadyWheel = redisUtils.hasKey(alreadyKey);

        if (alreadyWheel) {
            result.setAlreadyWheel(true);
            result.setMessage("????????????????????????");
            return result;
        }
        result.setAlreadyWheel(false);
        List<WheelInfo> wheelInfos = wheelInfoService.findWheelInfoByStatus(CommonStatusEnum.ENABLE.getKey());
        if (wheelInfos==null || wheelInfos.isEmpty()) {
            result.setMessage("?????????????????????????????????");
            return result;
        }
        //TODO 100?????????????????????????????????
        int randomNumber = RandomUtil.randomInt(0,100);
        String name = "";
        Integer integral = 0;
        logger.info("uid:{}??????????????????:{}",req.getUid(),randomNumber);
        int index = -1;
        for (int i=0;i<wheelInfos.size();i++) {

            WheelInfo wi = wheelInfos.get(i);
            logger.info("????????????:{},????????????:{}",wi.getStartNum(),wi.getEndNum());
            if (randomNumber>=wi.getStartNum() && randomNumber<wi.getEndNum()) {
                result.setIntegral(wi.getIntegral());
                integral = wi.getIntegral();
                result.setMessage("???????????????????????????+"+wi.getIntegral());
                index = i;
                name = wi.getName();
                break;
            }
        }
        if (index == -1) {
            result.setIntegral(0);
            result.setMessage("??????????????????????????????????????????????????????");
        }
        redisUtils.set(alreadyKey, integral.toString(), TimeUtils.diffSecondNowToDate(TimeUtils.getTodayEndTime()));
        buildUserWheelLog(name, integral,req.getUid(),randomNumber);
        //??????????????????????????????????????????
        index = 2*index + 1;
        result.setIndex(index<=0?0:index);
        return result;
    }

    private void buildUserWheelLog(String name, Integer integral, Integer uid, int randomNumber) {
        UserWheelLog userWheelLog = new UserWheelLog();
        WxUser user = new WxUser();
        user.setId(uid);
        userWheelLog.setRewardName(name);
        userWheelLog.setIntegral(integral);
        userWheelLog.setRandomNumber(randomNumber);
        userWheelLog.setUser(user);
        userWheelLog.setCreateTime(TimeUtils.getCurrentTimestamp());
        userWheelLogRepository.save(userWheelLog);
        if (integral>0) {
            //??????????????????
            ModifyUserIntegralReq req = new ModifyUserIntegralReq();
            req.setId(uid);
            req.setIntegral(integral);
            req.setRemark("??????");
            req.setStatus(IntegralTypeEnum.ADD_INTEGRAL.getType());
            userInfoService.saveUserIntegralLog(req);
        }
    }
}