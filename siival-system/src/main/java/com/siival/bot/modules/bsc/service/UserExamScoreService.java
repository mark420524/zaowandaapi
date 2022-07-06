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
package com.siival.bot.modules.bsc.service;

import com.siival.bot.modules.api.req.ExamScoreReq;
import com.siival.bot.modules.api.req.ExamSubmitReq;
import com.siival.bot.modules.api.resp.ExamScoreRankVo;
import com.siival.bot.modules.bsc.domain.UserExamScore;
import com.siival.bot.modules.bsc.service.dto.UserExamScoreDto;
import com.siival.bot.modules.bsc.service.dto.UserExamScoreQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @description 服务接口
* @author mark
* @date 2022-02-21
**/
public interface UserExamScoreService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserExamScoreQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UserExamScoreDto>
    */
    List<UserExamScoreDto> queryAll(UserExamScoreQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return UserExamScoreDto
     */
    UserExamScoreDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return UserExamScoreDto
    */
    UserExamScoreDto create(UserExamScore resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserExamScore resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<UserExamScoreDto> all, HttpServletResponse response) throws IOException;

    void saveUserExamScoreInfo(ExamSubmitReq req) throws Exception;

    List<ExamScoreRankVo> findExamScoreRankVoByPid(ExamScoreReq req);
}