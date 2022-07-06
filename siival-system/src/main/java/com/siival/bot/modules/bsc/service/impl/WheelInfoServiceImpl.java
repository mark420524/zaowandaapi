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

import com.siival.bot.enums.CommonStatusEnum;
import com.siival.bot.modules.api.resp.SysWheelInfoVo;
import com.siival.bot.modules.bsc.domain.WheelInfo;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.WheelInfoRepository;
import com.siival.bot.modules.bsc.service.WheelInfoService;
import com.siival.bot.modules.bsc.service.dto.WheelInfoDto;
import com.siival.bot.modules.bsc.service.dto.WheelInfoQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.WheelInfoMapper;
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
* @date 2022-04-21
**/
@Service
@RequiredArgsConstructor
public class WheelInfoServiceImpl implements WheelInfoService {

    private final WheelInfoRepository wheelInfoRepository;
    private final WheelInfoMapper wheelInfoMapper;

    @Override
    public Map<String,Object> queryAll(WheelInfoQueryCriteria criteria, Pageable pageable){
        Page<WheelInfo> page = wheelInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(wheelInfoMapper::toDto));
    }

    @Override
    public List<WheelInfoDto> queryAll(WheelInfoQueryCriteria criteria){
        return wheelInfoMapper.toDto(wheelInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public WheelInfoDto findById(Integer id) {
        WheelInfo wheelInfo = wheelInfoRepository.findById(id).orElseGet(WheelInfo::new);
        ValidationUtil.isNull(wheelInfo.getId(),"WheelInfo","id",id);
        return wheelInfoMapper.toDto(wheelInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WheelInfoDto create(WheelInfo resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return wheelInfoMapper.toDto(wheelInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(WheelInfo resources) {
        WheelInfo wheelInfo = wheelInfoRepository.findById(resources.getId()).orElseGet(WheelInfo::new);
        ValidationUtil.isNull( wheelInfo.getId(),"WheelInfo","id",resources.getId());
        wheelInfo.copy(resources);
        wheelInfo.setUpdateTime(TimeUtils.getCurrentTimestamp());
        wheelInfoRepository.save(wheelInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            wheelInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<WheelInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WheelInfoDto wheelInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("奖项名称", wheelInfo.getName());
            map.put("奖项赠送积分", wheelInfo.getIntegral());
            map.put("状态", wheelInfo.getStatus());
            map.put("随机数起始值", wheelInfo.getStartNum());
            map.put("排序值", wheelInfo.getSort());
            map.put("随机数结束值", wheelInfo.getEndNum());
            map.put("备注", wheelInfo.getRemark());
            map.put("创建日期", wheelInfo.getCreateTime());
            map.put("更新时间", wheelInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<SysWheelInfoVo> findEnableWheelInfo() {
        List<WheelInfo> list = wheelInfoRepository.findByStatusOrderBySortAsc(CommonStatusEnum.ENABLE.getKey());
        if (list==null || list.isEmpty()) {
            return null;
        }
        List<SysWheelInfoVo> vos = list.stream().map(s->{
            SysWheelInfoVo vo = new SysWheelInfoVo();
            vo.setIntegral(s.getIntegral());
            vo.setRemark(s.getRemark());
            vo.setName(s.getName());
            return vo;
        }).collect(Collectors.toList());
        return vos;
    }

    @Override
    public List<WheelInfo> findWheelInfoByStatus(Integer status) {
        return wheelInfoRepository.findByStatusOrderBySortAsc(status);
    }
}