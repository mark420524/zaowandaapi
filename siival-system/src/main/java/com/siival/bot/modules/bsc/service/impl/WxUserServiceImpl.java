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

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.siival.bot.config.FileProperties;
import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.enums.IntegralTypeEnum;
import com.siival.bot.exception.BadRequestException;
import com.siival.bot.modules.api.constant.PdfHandleConstant;
import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.modules.api.req.UserInfo;
import com.siival.bot.modules.bsc.constant.SystemSettingConstant;
import com.siival.bot.modules.bsc.domain.UserInviteLog;
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.repository.UserInviteLogRepository;
import com.siival.bot.modules.bsc.req.ModifyUserIntegralReq;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.UserInfoService;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.WxUserRepository;
import com.siival.bot.modules.bsc.service.WxUserService;
import com.siival.bot.modules.bsc.service.dto.WxUserDto;
import com.siival.bot.modules.bsc.service.dto.WxUserQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.WxUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website
* @description 服务实现
* @author mark
* @date 2022-01-16
**/
@Service
public class WxUserServiceImpl implements WxUserService {

    private Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Autowired
    private   WxUserRepository wxUserRepository;
    @Autowired
    private   WxUserMapper wxUserMapper;
    @Autowired
    private   UserInviteLogRepository userInviteLogRepository;
    @Autowired
    private   UserInfoService userInfoService;
    @Autowired
    private   RedisUtils redisUtils;
    @Autowired
    private   SysSettingService sysSettingService;
    @Autowired
    private   FileProperties fileProperties;
    @Autowired
    private   WxUserService wxUserService;

    @Override
    public Map<String,Object> queryAll(WxUserQueryCriteria criteria, Pageable pageable){
        Page<WxUser> page = wxUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wxUserMapper::toDto));
    }

    @Override
    public List<WxUserDto> queryAll(WxUserQueryCriteria criteria){
        return wxUserMapper.toDto(wxUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WxUserDto findById(Integer id) {
        WxUser wxUser = wxUserRepository.findById(id).orElseGet(WxUser::new);
        ValidationUtil.isNull(wxUser.getId(),"WxUser","id",id);
        return wxUserMapper.toDto(wxUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxUserDto create(WxUser resources) {
        return wxUserMapper.toDto(wxUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WxUser resources) {
        WxUser wxUser = wxUserRepository.findById(resources.getId()).orElseGet(WxUser::new);
        ValidationUtil.isNull( wxUser.getId(),"WxUser","id",resources.getId());
        wxUser.copy(resources);
        wxUserRepository.save(wxUser);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            wxUserRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<WxUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WxUserDto wxUser : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("昵称", wxUser.getNickName());
            map.put("头像地址", wxUser.getAvatarUrl());
            map.put("国家", wxUser.getCountry());
            map.put("性别", wxUser.getGender());
            map.put("语言", wxUser.getLanguage());
            map.put("省份", wxUser.getProvince());
            map.put("unionid", wxUser.getUnionid());
            map.put("openid", wxUser.getOpenid());
            map.put("session key", wxUser.getSessionKey());
            map.put("是否启用", wxUser.getStatus());
            map.put("创建时间", wxUser.getCreateTime());
            map.put("更新时间", wxUser.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public WxUserDto findUserByUnionidOrOpenId(String unionId, String openid) {
        WxUser user = null;
        if (!StringUtils.isAllBlank(unionId)) {
            user = wxUserRepository.findFirstByUnionid(unionId);
        }else{
            user =   wxUserRepository.findFirstByOpenid(openid);
        }
        return wxUserMapper.toDto(user);
    }

    @Override
    public Integer saveOrFindUserIdBySession(WxMaJscode2SessionResult session,String appId) {
        return saveOrFindUserIdBySession(session, null,appId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOrFindUserIdBySession(WxMaJscode2SessionResult session,Integer inviteUid, String appId) {
        WxUser user = wxUserRepository.findFirstByUnionid(session.getUnionid());
        if (user!=null) {
            return user.getId();
        }
        user = wxUserRepository.findFirstByOpenid(session.getOpenid());
//        WxUserDto dto = findUserByUnionidOrOpenId(session.getUnionid(), session.getOpenid());
        if (user!=null) {
            //老用户update union id
            user.setUnionid(session.getUnionid());
            user.setUpdateTime(TimeUtils.getCurrentTimestamp());
            wxUserRepository.save(user);
            return user.getId();
        }
        user = new WxUser();
        user.setAppId(appId);
        user.setSessionKey(session.getSessionKey());
        user.setUnionid(session.getUnionid());
        user.setOpenid(session.getOpenid());
        user.setStatus(CommonStatusEnum.ENABLE.getKey());
        wxUserRepository.save(user);
        Integer newUserId = user.getId();
        if (inviteUid!=null && inviteUid>0) {
            logger.info("用户id:{},邀请用户id:{}",inviteUid,newUserId);
            buildUserInviteLog(inviteUid, user);
        }
        return newUserId;
    }

    private void buildUserInviteLog(Integer inviteUid, WxUser newUser) {
        long count = userInviteLogRepository.countByUserIdAndInviteUserId(inviteUid, newUser.getId());
        if (count>0) {
            logger.info("该邀请已记录历史");
            return;
        }
        UserInviteLog inviteLog = new UserInviteLog();

        Integer integral = sysSettingService.findSystemSettingValueToInteger(SystemSettingConstant.INVITE_SHARE_INTEGRAL);
        
        WxUser user = new WxUser();
        user.setId(inviteUid);
        inviteLog.setUser(user);
        inviteLog.setIntegral(integral);
        inviteLog.setInviteUser(newUser);
        inviteLog.setCreateTime(TimeUtils.getCurrentTimestamp());
        userInviteLogRepository.save(inviteLog);

        logger.info("用户:{}邀请,赠送积分:{}",inviteUid,integral);
        ModifyUserIntegralReq req = new ModifyUserIntegralReq();
        req.setIntegral(integral);
        req.setRemark("邀请用户");
        req.setId(inviteUid);
        req.setStatus(IntegralTypeEnum.ADD_INTEGRAL.getType());
        userInfoService.saveUserIntegralLog(req);
    }

    @Override
    @Transactional
    public String updateUserInfo(UserInfo userInfo) throws Exception {
        UserInfo oldInfo = wxUserService.findUserInfoByUid(userInfo.getUid());
        if (StringUtils.isBlank(userInfo.getNickName())) {
            userInfo.setNickName(oldInfo.getNickName());
        }
        if (StringUtils.isBlank(userInfo.getAvatarUrl())) {
            userInfo.setAvatarUrl(oldInfo.getAvatarUrl());
        }
        //2022年6月25日 10点23分 微信获取信息改了，次数要先处理头像信息
        MultipartFile file = userInfo.getFile();
        String avatarUrl = "";
        if (file!=null && !file.isEmpty()) {
            logger.info("微信小程序新版获取头像信息");
            checkFileTypeAndSize(file);
            String rootFolder = fileProperties.getPath().getAvatar();
            String folder = "wxuser";
            Path path = Paths.get(rootFolder, folder);
            File tempFile = FileUtil.upload(file, path.toString());
            avatarUrl = fileProperties.getImageDomain() + folder + "/" + tempFile.getName();
            userInfo.setAvatarUrl(avatarUrl);
        }

        wxUserRepository.updateUserInfoById(userInfo.getUid(), userInfo.getNickName(),
                userInfo.getAvatarUrl(), userInfo.getCountry(), userInfo.getGender(),
                userInfo.getLanguage(), userInfo.getProvince(), new Timestamp(System.currentTimeMillis()));
        return avatarUrl;
    }

    @Override
    public UserInfo findUserInfoByUid(Integer uid) {
        WxUser  user = wxUserRepository.findById(uid).orElseGet(WxUser::new);
        UserInfo info = new UserInfo();
        info.setAvatarUrl(user.getAvatarUrl());
        info.setNickName(user.getNickName());

        return info;
    }

    private void checkFileTypeAndSize(MultipartFile file) {
        String image = "gif jpg png jpeg";
        String fileType = FileUtil.getExtensionName(file.getOriginalFilename());
        if(fileType != null && !image.contains(fileType)){
            throw new BadRequestException("文件格式错误！, 仅支持 " + image +" 格式");
        }
        FileUtil.checkSize(fileProperties.getMaxSize(), file.getSize());
    }
}