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

import com.siival.bot.exception.EntityExistException;
import com.siival.bot.modules.bsc.domain.SysSetting;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.SysSettingRepository;
import com.siival.bot.modules.bsc.service.SysSettingService;
import com.siival.bot.modules.bsc.service.dto.SysSettingDto;
import com.siival.bot.modules.bsc.service.dto.SysSettingQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.SysSettingMapper;
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
* @description 服务实现
* @author mark
* @date 2022-05-06
**/
@Service
@RequiredArgsConstructor
public class SysSettingServiceImpl implements SysSettingService {

    private final SysSettingRepository sysSettingRepository;
    private final SysSettingMapper sysSettingMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(SysSettingQueryCriteria criteria, Pageable pageable){
        Page<SysSetting> page = sysSettingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysSettingMapper::toDto));
    }

    @Override
    public List<SysSettingDto> queryAll(SysSettingQueryCriteria criteria){
        return sysSettingMapper.toDto(sysSettingRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysSettingDto findById(Integer id) {
        SysSetting sysSetting = sysSettingRepository.findById(id).orElseGet(SysSetting::new);
        ValidationUtil.isNull(sysSetting.getId(),"SysSetting","id",id);
        return sysSettingMapper.toDto(sysSetting);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysSettingDto create(SysSetting resources) {
        if(sysSettingRepository.findFirstByName(resources.getName()) != null){
            throw new EntityExistException(SysSetting.class,"name",resources.getName());
        }
        setRedisValueCache(resources.getName(), resources.getValue());
        return sysSettingMapper.toDto(sysSettingRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysSetting resources) {
        SysSetting sysSetting = sysSettingRepository.findById(resources.getId()).orElseGet(SysSetting::new);
        ValidationUtil.isNull( sysSetting.getId(),"SysSetting","id",resources.getId());
        SysSetting sysSetting1 = null;
        sysSetting1 = sysSettingRepository.findFirstByName(resources.getName());
        if(sysSetting1 != null && !sysSetting1.getId().equals(sysSetting.getId())){
            throw new EntityExistException(SysSetting.class,"name",resources.getName());
        }
        sysSetting.copy(resources);
        setRedisValueCache(sysSetting.getName(), sysSetting.getValue());
        sysSettingRepository.save(sysSetting);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            sysSettingRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysSettingDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysSettingDto sysSetting : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("设置key", sysSetting.getName());
            map.put("设置值", sysSetting.getValue());
            map.put("描述", sysSetting.getRemark());
            map.put("创建者", sysSetting.getCreateBy());
            map.put("更新者", sysSetting.getUpdateBy());
            map.put("创建日期", sysSetting.getCreateTime());
            map.put("更新时间", sysSetting.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public String findSystemSettingValue(String settingKey) {
        Object val = redisUtils.get(settingKey);
        if (val==null || StringUtils.isBlank(val.toString())) {
            SysSetting setting = sysSettingRepository.findFirstByName(settingKey);
            val = setting==null||setting.getValue()==null?"":setting.getValue();
            if (StringUtils.isNotBlank(val.toString())) {
                redisUtils.set(settingKey, val);
            }
        }
        return val.toString();
    }

    public void setRedisValueCache(String key,String value) {
        redisUtils.set(key, value);
    }

    @Override
    public Integer findSystemSettingValueToInteger(String settingKey) {
        String val = findSystemSettingValue(settingKey);
        try {
            return  Integer.valueOf(val);
        }catch (NumberFormatException e){
            return 0;
        }
    }
}