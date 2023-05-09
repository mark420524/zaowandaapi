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

import com.siival.bot.modules.api.req.ExamScoreReq;
import com.siival.bot.modules.api.req.ExamSubmitReq;
import com.siival.bot.modules.api.resp.ExamScoreRankVo;
import com.siival.bot.modules.bsc.domain.UserExamScore;
import com.siival.bot.utils.*;
import lombok.RequiredArgsConstructor;
import com.siival.bot.modules.bsc.repository.UserExamScoreRepository;
import com.siival.bot.modules.bsc.service.UserExamScoreService;
import com.siival.bot.modules.bsc.service.dto.UserExamScoreDto;
import com.siival.bot.modules.bsc.service.dto.UserExamScoreQueryCriteria;
import com.siival.bot.modules.bsc.service.mapstruct.UserExamScoreMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
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
* @date 2022-02-21
**/
@Service
@RequiredArgsConstructor
public class UserExamScoreServiceImpl implements UserExamScoreService {

    private final UserExamScoreRepository userExamScoreRepository;
    private final UserExamScoreMapper userExamScoreMapper;

    @Override
    public Map<String,Object> queryAll(UserExamScoreQueryCriteria criteria, Pageable pageable){
        Page<UserExamScore> page = userExamScoreRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userExamScoreMapper::toDto));
    }

    @Override
    public List<UserExamScoreDto> queryAll(UserExamScoreQueryCriteria criteria){
        return userExamScoreMapper.toDto(userExamScoreRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserExamScoreDto findById(Integer id) {
        UserExamScore userExamScore = userExamScoreRepository.findById(id).orElseGet(UserExamScore::new);
        ValidationUtil.isNull(userExamScore.getId(),"UserExamScore","id",id);
        return userExamScoreMapper.toDto(userExamScore);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserExamScoreDto create(UserExamScore resources) {
        resources.setCreateTime(TimeUtils.getCurrentTimestamp());
        return userExamScoreMapper.toDto(userExamScoreRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserExamScore resources) {
        UserExamScore userExamScore = userExamScoreRepository.findById(resources.getId()).orElseGet(UserExamScore::new);
        ValidationUtil.isNull( userExamScore.getId(),"UserExamScore","id",resources.getId());
        userExamScore.copy(resources);
        userExamScoreRepository.save(userExamScore);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            userExamScoreRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserExamScoreDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserExamScoreDto userExamScore : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("题库分类id", userExamScore.getPid());
            map.put("得分", userExamScore.getScore());
            map.put("用户id", userExamScore.getUid());
            map.put("错题数量", userExamScore.getWrong());
            map.put("创建时间", userExamScore.getCreateTime());
            map.put("更新时间", userExamScore.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserExamScoreInfo(ExamSubmitReq req) throws Exception {
        UserExamScore ues = new UserExamScore();
        ues.setUid(req.getUid());
        ues.setScore(req.getRight());
        ues.setWrong(req.getWrong());
        ues.setPid(req.getCid());
        ues.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userExamScoreRepository.save(ues);
    }

    @Override
    public List<ExamScoreRankVo> findExamScoreRankVoByPid(ExamScoreReq req) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"score"),new Sort.Order(Sort.Direction.ASC, "createTime"));
        Pageable pageable =   PageRequest.of(req.getPage(), req.getSize(),sort  );
        List<Object[]> list = userExamScoreRepository.selectExamScoreRankVo(req.getCid(),pageable);
        if (list==null||list.isEmpty()) {
            return new ArrayList<>();
        }
        return list.stream().map(s->{
//            wu.nickName,ues.score,wu.avatarUrl,ues.createTime
            ExamScoreRankVo vo = new ExamScoreRankVo();
            if (s[0]!=null) {
                vo.setNickName(s[0].toString().length()>10?s[0].toString().substring(0,11)+"...":s[0].toString());
            }else{
                vo.setNickName("");
            }
            if (s[1]!=null){
                try {
                    vo.setScore(Integer.valueOf(s[1].toString()));
                }catch (NumberFormatException e){
                    vo.setScore(0);
                }
            }else{
                vo.setScore(0);
            }
            vo.setAvatarUrl(s[2]==null?"":s[2].toString());
            vo.setCreateTime(s[3]==null?null:s[3].toString().substring(0,s[3].toString().indexOf(".")));
            return vo;
        }).collect(Collectors.toList());
    }
}