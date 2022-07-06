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
import com.siival.bot.modules.bsc.domain.ExamMenu;
import com.siival.bot.modules.bsc.domain.QuestionMenu;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.ExamMenuRepository;
import com.siival.bot.modules.bsc.service.ExamMenuService;
import com.siival.bot.modules.bsc.service.dto.ExamMenuDto;
import com.siival.bot.modules.bsc.service.dto.ExamMenuQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.ExamMenuMapper;
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
* @date 2022-04-01
**/
@Service
@RequiredArgsConstructor
public class ExamMenuServiceImpl implements ExamMenuService {

    private final ExamMenuRepository examMenuRepository;
    private final ExamMenuMapper examMenuMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(ExamMenuQueryCriteria criteria, Pageable pageable){
        Page<ExamMenu> page = examMenuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(examMenuMapper::toDto));
    }

    @Override
    public List<ExamMenuDto> queryAll(ExamMenuQueryCriteria criteria){
        List<ExamMenuDto> list = examMenuMapper.toDto(examMenuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        if (list!=null) {
            return list.stream().map(s->{
                s.setHasChildren(findMenuChildrenCount(s.getId())>0);
                return s;
            }).collect(Collectors.toList());
        }
        return null;
    }

    public long findMenuChildrenCount(Integer id) {
        return examMenuRepository.countByPid(id);
    }

    @Override
    @Transactional
    public ExamMenuDto findById(Integer id) {
        ExamMenu examMenu = examMenuRepository.findById(id).orElseGet(ExamMenu::new);
        ValidationUtil.isNull(examMenu.getId(),"ExamMenu","id",id);
        return examMenuMapper.toDto(examMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExamMenuDto create(ExamMenu resources) {
        return examMenuMapper.toDto(examMenuRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ExamMenu resources) {
        ExamMenu examMenu = examMenuRepository.findById(resources.getId()).orElseGet(ExamMenu::new);
        ValidationUtil.isNull( examMenu.getId(),"ExamMenu","id",resources.getId());
        examMenu.copy(resources);
        examMenuRepository.save(examMenu);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            examMenuRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ExamMenuDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExamMenuDto examMenu : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("菜单名称", examMenu.getName());
            map.put("状态", examMenu.getStatus());
            map.put("创建时间", examMenu.getCreateTime());
            map.put("更新时间", examMenu.getUpdateTime());
            map.put("上级标签", examMenu.getPid());
            map.put("排序值", examMenu.getSort());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<CategoryInfo> getEnableMenuInfo(Integer pid) {
        Object obj  = redisUtils.get(RedisKeyConstant.EXAM_MENU_INFO);
        List<CategoryInfo> list = null;
        if (obj == null) {
            list = getAllEnableMenuInfoFromDb(pid);
            redisUtils.set(RedisKeyConstant.EXAM_MENU_INFO, JSONUtil.toJsonStr(list));
        }else{
            JSONArray array = JSONUtil.parseArray(obj.toString());
            list = JSONUtil.toList(array,CategoryInfo.class);
        }
        return list;
    }

    @Override
    public List<CategoryInfo> getAllEnableMenuInfoFromDb(Integer pid) {
        List<ExamMenu> menuList = examMenuRepository.findByStatusAndPidOrderBySortAsc(CommonStatusEnum.ENABLE.getKey(), pid);
        return menuList==null?null:menuList.stream().map(s->{
            CategoryInfo ci = new CategoryInfo();
            ci.setId(s.getId());
            ci.setName(s.getName());
            ci.setChildren(getAllEnableMenuInfoFromDb(ci.getId()));
            return ci;
        }).collect(Collectors.toList());

    }

    @Override
    public void syncExamMenuInfo() {
        List<CategoryInfo> list =  getAllEnableMenuInfoFromDb(0);
        redisUtils.set(RedisKeyConstant.EXAM_MENU_INFO, JSONUtil.toJsonStr(list));
    }
}