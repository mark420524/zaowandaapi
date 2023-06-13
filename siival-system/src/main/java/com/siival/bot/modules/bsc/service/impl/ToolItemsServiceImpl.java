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
import com.siival.bot.modules.api.resp.ToolsItemInfo;
import com.siival.bot.modules.bsc.domain.ToolItems;
import com.siival.bot.modules.bsc.repository.ToolItemsRepository;
import com.siival.bot.modules.bsc.service.ToolItemsService;
import com.siival.bot.modules.bsc.service.dto.ToolItemsDto;
import com.siival.bot.modules.bsc.service.dto.ToolItemsQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.ToolItemsMapper;
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
public class ToolItemsServiceImpl implements ToolItemsService {
    @Autowired
    private   ToolItemsRepository toolItemsRepository;
    @Autowired
    private   ToolItemsMapper toolItemsMapper;

    @Override
    public Map<String,Object> queryAll(ToolItemsQueryCriteria criteria, Pageable pageable){
        Page<ToolItems> page = toolItemsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(toolItemsMapper::toDto));
    }

    @Override
    public List<ToolItemsDto> queryAll(ToolItemsQueryCriteria criteria){
        return toolItemsMapper.toDto(toolItemsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ToolItemsDto findById(Integer id) {
        ToolItems toolItems = toolItemsRepository.findById(id).orElseGet(ToolItems::new);
        ValidationUtil.isNull(toolItems.getId(),"ToolItems","id",id);
        return toolItemsMapper.toDto(toolItems);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ToolItemsDto create(ToolItems resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return toolItemsMapper.toDto(toolItemsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ToolItems resources) {
        ToolItems toolItems = toolItemsRepository.findById(resources.getId()).orElseGet(ToolItems::new);
        ValidationUtil.isNull( toolItems.getId(),"ToolItems","id",resources.getId());
        toolItems.copy(resources);
        toolItems.setUpdateTime(TimeUtils.getCurrentTimestamp());
        toolItemsRepository.save(toolItems);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            toolItemsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ToolItemsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ToolItemsDto toolItems : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("显示文字", toolItems.getText());
            map.put("跳转url", toolItems.getUrl());
            map.put("跳转类型", toolItems.getLinkType());
            map.put("图标颜色", toolItems.getIconColor());
            map.put("图标", toolItems.getIcon());
            map.put("状态", toolItems.getStatus());
            map.put("排序值", toolItems.getSort());
            map.put("创建者", toolItems.getCreateBy());
            map.put("更新者", toolItems.getUpdateBy());
            map.put("创建日期", toolItems.getCreateTime());
            map.put("更新时间", toolItems.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<ToolsItemInfo> findToolsItemInfo() {
        List<ToolItems> list = toolItemsRepository.findByStatusOrderBySortAsc(CommonStatusEnum.ENABLE.getKey());
        if (list==null || list.isEmpty()) {
            return new ArrayList<>();
        }

        return list.stream().map(s->{
            ToolsItemInfo info = new ToolsItemInfo();
            info.setText(s.getText());
            info.setUrl(s.getUrl());
            info.setLinkType(s.getLinkType());
            info.setIcon(s.getIcon());
            info.setIconColor(s.getIconColor());
            return info;
        }).collect(Collectors.toList());
    }
}