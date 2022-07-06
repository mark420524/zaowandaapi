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

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.modules.api.constant.RedisKeyConstant;
import com.siival.bot.modules.api.resp.CategoryInfo;
import com.siival.bot.modules.bsc.domain.QuestionMenu;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.QuestionMenuRepository;
import com.siival.bot.modules.bsc.service.QuestionMenuService;
import com.siival.bot.modules.bsc.service.dto.QuestionMenuDto;
import com.siival.bot.modules.bsc.service.dto.QuestionMenuQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.QuestionMenuMapper;
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
* @author mark
* @date 2022-01-17
**/
@Service
@RequiredArgsConstructor
public class QuestionMenuServiceImpl implements QuestionMenuService {

    private final QuestionMenuRepository questionMenuRepository;
    private final QuestionMenuMapper questionMenuMapper;

    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(QuestionMenuQueryCriteria criteria, Pageable pageable){
        Page<QuestionMenu> page = questionMenuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(questionMenuMapper::toDto));
    }

    @Override
    public List<QuestionMenuDto> queryAll(QuestionMenuQueryCriteria criteria){

        List<QuestionMenuDto> list = questionMenuMapper.toDto(questionMenuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        if (list!=null) {
            return list.stream().map(s->{
               s.setHasChildren(findMenuChildrenCount(s.getId())>0);
               return s;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public QuestionMenuDto findById(Integer id) {
        QuestionMenu questionMenu = questionMenuRepository.findById(id).orElseGet(QuestionMenu::new);
        ValidationUtil.isNull(questionMenu.getId(),"QuestionMenu","id",id);
        return questionMenuMapper.toDto(questionMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionMenuDto create(QuestionMenu resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return questionMenuMapper.toDto(questionMenuRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QuestionMenu resources) {
        QuestionMenu questionMenu = questionMenuRepository.findById(resources.getId()).orElseGet(QuestionMenu::new);
        ValidationUtil.isNull( questionMenu.getId(),"QuestionMenu","id",resources.getId());
        questionMenu.copy(resources);
        questionMenu.setUpdateTime(TimeUtils.getCurrentTimestamp());
        questionMenuRepository.save(questionMenu);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            questionMenuRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QuestionMenuDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuestionMenuDto questionMenu : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题目", questionMenu.getName());
            map.put("是否启用", questionMenu.getStatus());
            map.put("创建时间", questionMenu.getCreateTime());
            map.put("更新时间", questionMenu.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<CategoryInfo> getEnableMenuInfo(Integer pid) {
        Object obj  = redisUtils.get(RedisKeyConstant.QUESTION_MENU_INFO);
        List<CategoryInfo> list = null;
        if (obj == null) {
            list = getAllEnableMenuInfoFromDb(pid);
            redisUtils.set(RedisKeyConstant.QUESTION_MENU_INFO, JSONUtil.toJsonStr(list));
        }else{
            JSONArray array = JSONUtil.parseArray(obj.toString());
            list = JSONUtil.toList(array,CategoryInfo.class);
        }
        return list;
    }

    @Override
    public List<CategoryInfo> getAllEnableMenuInfoFromDb(Integer pid) {
        List<QuestionMenu> menuList = questionMenuRepository.findByStatusAndPidOrderBySortAsc(CommonStatusEnum.ENABLE.getKey(), pid);
        return menuList==null?null:menuList.stream().map(s->{
            CategoryInfo ci = new CategoryInfo();
            ci.setId(s.getId());
            ci.setName(s.getName());
            ci.setChildren(getAllEnableMenuInfoFromDb(ci.getId()));
            return ci;
        }).collect(Collectors.toList());

    }

    @Override
    public List<CategoryInfo> getAllMenuInfoFromDb(Integer pid) {
        List<QuestionMenu> menuList = questionMenuRepository.findByPidOrderBySortAsc(pid);
        return menuList==null?null:menuList.stream().map(s->{
            CategoryInfo ci = new CategoryInfo();
            ci.setId(s.getId());
            ci.setName(s.getName());
            ci.setChildren(getAllMenuInfoFromDb(ci.getId()));
            return ci;
        }).collect(Collectors.toList());

    }

    @Override
    public Long findMenuChildrenCount(Integer pid) {
        return questionMenuRepository.countByPid(pid);
    }

    @Override
    public void syncMenuInfo() {
        List<CategoryInfo> list =  getAllEnableMenuInfoFromDb(0);
        redisUtils.set(RedisKeyConstant.QUESTION_MENU_INFO, JSONUtil.toJsonStr(list));
        Object obj = redisUtils.get(RedisKeyConstant.QUESTION_MENU_REFRESH);
        obj = obj==null?"0":obj;
        Integer val = Integer.valueOf(obj.toString());
        val++;
        redisUtils.set(RedisKeyConstant.QUESTION_MENU_REFRESH, val.toString());
    }
}