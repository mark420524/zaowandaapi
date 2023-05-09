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

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.modules.bsc.domain.WxApp;
import com.siival.bot.modules.bsc.res.AppNameVo;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.WxAppRepository;
import com.siival.bot.modules.bsc.service.WxAppService;
import com.siival.bot.modules.bsc.service.dto.WxAppDto;
import com.siival.bot.modules.bsc.service.dto.WxAppQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.WxAppMapper;
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
import java.util.stream.Collectors;

/**
* @website
* @description 服务实现
* @author Mark
* @date 2022-04-02
**/
@Service
@RequiredArgsConstructor
public class WxAppServiceImpl implements WxAppService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WxAppRepository wxAppRepository;
    private final WxAppMapper wxAppMapper;
    private static Map<String, WxMaService> maServices;

    @Override
    public Map<String,Object> queryAll(WxAppQueryCriteria criteria, Pageable pageable){
        Page<WxApp> page = wxAppRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wxAppMapper::toDto));
    }

    @Override
    public List<WxAppDto> queryAll(WxAppQueryCriteria criteria){
        return wxAppMapper.toDto(wxAppRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WxAppDto findById(Integer id) {
        WxApp wxApp = wxAppRepository.findById(id).orElseGet(WxApp::new);
        ValidationUtil.isNull(wxApp.getId(),"WxApp","id",id);
        return wxAppMapper.toDto(wxApp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxAppDto create(WxApp resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        putWxAppService(resources.getAppId(), resources);
        return wxAppMapper.toDto(wxAppRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WxApp resources) {
        WxApp wxApp = wxAppRepository.findById(resources.getId()).orElseGet(WxApp::new);
        ValidationUtil.isNull( wxApp.getId(),"WxApp","id",resources.getId());
        wxApp.copy(resources);
        putWxAppService(resources.getAppId(), wxApp);
        wxAppRepository.save(wxApp);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            wxAppRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<WxAppDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WxAppDto wxApp : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("app id", wxApp.getAppId());
            map.put("app secret", wxApp.getAppSecret());
            map.put("token", wxApp.getAppToken());
            map.put("aes加密key", wxApp.getAesKey());
            map.put("消息格式", wxApp.getMsgDataFormat());
            map.put("类型", wxApp.getType());
            map.put("名称", wxApp.getName());
            map.put("备注", wxApp.getRemark());
            map.put("状态", wxApp.getStatus());
            map.put("创建时间", wxApp.getCreateTime());
            map.put("更新时间", wxApp.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<AppNameVo> findEnableWxAppInfo() {
        List<WxApp> list = wxAppRepository.findByStatusOrderByIdDesc(CommonStatusEnum.ENABLE.getKey());
        if (list==null || list.isEmpty()) {
            return null;
        }
        return list.stream().map(s->{
            AppNameVo vo = new AppNameVo();
            vo.setId(s.getId());
            vo.setName(s.getName());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void initEnableWxAppService() {
        List<WxApp> list = wxAppRepository.findByStatusOrderByIdDesc(CommonStatusEnum.ENABLE.getKey());
        logger.info("开始初始化wx app信息");
        if (list==null || list.isEmpty()) {
            return;
        }
        maServices = list.stream()
                .map(a -> {
                    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();

                    config.setAppid(a.getAppId());
                    config.setSecret(a.getAppSecret());
                    config.setToken(a.getAppToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMsgDataFormat());

                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
//                routers.put(a.getAppid(), this.newRouter(service));
                    return service;
                }).collect(Collectors.toMap(a -> a.getWxMaConfig().getAppid(), a -> a, (k1,k2)-> k1 ) );

    }

    public WxMaService  getWxAppService(String appId) {
        WxMaService service = maServices.get(appId);
        if (service!=null) {
            return service;
        }
        WxApp app = wxAppRepository.findFirstByAppId(appId);
        logger.info("获取app id:{}配置的service",appId);
        if (app==null || CommonStatusEnum.DISABLE.getKey().equals(app.getStatus())) {
            return null;
        }
        service = putWxAppService(appId, app);
        return service;
    }

    public synchronized WxMaService putWxAppService(String appId, WxApp app ){
        if (CommonStatusEnum.DISABLE.getKey().equals(app.getStatus())) {
            return null;
        }
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();

        config.setAppid(app.getAppId());
        config.setSecret(app.getAppSecret());
        config.setToken(app.getAppToken());
        config.setAesKey(app.getAesKey());
        config.setMsgDataFormat(app.getMsgDataFormat());

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        maServices.put(appId, service);
        return service;
    }
}