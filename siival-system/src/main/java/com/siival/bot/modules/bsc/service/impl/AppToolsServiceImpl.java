/*
*  Copyright 2019-2020
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

import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.modules.api.resp.AppToolsItem;
import com.siival.bot.modules.bsc.domain.AppTools;
import com.siival.bot.modules.bsc.repository.AppToolsRepository;
import com.siival.bot.modules.bsc.service.AppToolsService;
import com.siival.bot.modules.bsc.service.dto.AppToolsDto;
import com.siival.bot.modules.bsc.service.dto.AppToolsQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.AppToolsMapper;
import com.siival.bot.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*
* @Description  服务实现
* @author Mark
* @date 2022-07-29
**/
@Service
public class AppToolsServiceImpl implements AppToolsService {

    @Autowired
    private   AppToolsRepository appToolsRepository;
    @Autowired
    private   AppToolsMapper appToolsMapper;

    @Override
    public Map<String,Object> queryAll(AppToolsQueryCriteria criteria, Pageable pageable){
        Page<AppTools> page = appToolsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(appToolsMapper::toDto));
    }

    @Override
    public List<AppToolsDto> queryAll(AppToolsQueryCriteria criteria){
        return appToolsMapper.toDto(appToolsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public AppToolsDto findById(Integer id) {
        AppTools appTools = appToolsRepository.findById(id).orElseGet(AppTools::new);
        ValidationUtil.isNull(appTools.getId(),"AppTools","id",id);
        return appToolsMapper.toDto(appTools);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppToolsDto create(AppTools resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return appToolsMapper.toDto(appToolsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AppTools resources) {
        AppTools appTools = appToolsRepository.findById(resources.getId()).orElseGet(AppTools::new);
        ValidationUtil.isNull( appTools.getId(),"AppTools","id",resources.getId());
        appTools.copy(resources);
        appTools.setUpdateTime(TimeUtils.getCurrentTimestamp());
        appToolsRepository.save(appTools);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            appToolsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AppToolsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppToolsDto appTools : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("显示文字", appTools.getText());
            map.put("跳转路径", appTools.getPath());
            map.put("小程序appId", appTools.getAppId());
            map.put("图标颜色", appTools.getIconColor());
            map.put("图标", appTools.getIcon());
            map.put("状态", appTools.getStatus());
            map.put("排序值", appTools.getSort());
            map.put("创建者", appTools.getCreateBy());
            map.put("更新者", appTools.getUpdateBy());
            map.put("创建日期", appTools.getCreateTime());
            map.put("更新时间", appTools.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<AppToolsItem> findAppToolsItem() {
        List<AppTools> list = appToolsRepository.findByStatusOrderBySortAsc(CommonStatusEnum.ENABLE.getKey());
        if (list==null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(s->{
            AppToolsItem item = new AppToolsItem();
            item.setAppId(s.getAppId());
            item.setIcon(s.getIcon());
            item.setIconColor(s.getIconColor());
            item.setPath(s.getPath());
            item.setText(s.getText());
            return item;
        }).collect(Collectors.toList());
    }
}