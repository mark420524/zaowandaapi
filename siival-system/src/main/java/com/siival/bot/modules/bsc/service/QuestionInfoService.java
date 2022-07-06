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

import com.siival.bot.modules.api.req.*;
import com.siival.bot.modules.api.resp.ExportInfo;
import com.siival.bot.modules.api.resp.QuestionsVo;
import com.siival.bot.modules.api.resp.TodayQuestionInfo;
import com.siival.bot.modules.bsc.domain.QuestionInfo;
import com.siival.bot.modules.bsc.service.dto.QuestionInfoDto;
import com.siival.bot.modules.bsc.service.dto.QuestionInfoQueryCriteria;
import com.siival.bot.resp.PageResult;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website
* @description 服务接口
* @author mark
* @date 2022-02-12
**/
public interface QuestionInfoService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(QuestionInfoQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<QuestionInfoDto>
    */
    List<QuestionInfoDto> queryAll(QuestionInfoQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return QuestionInfoDto
     */
    QuestionInfoDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return QuestionInfoDto
    */
    QuestionInfoDto create(QuestionInfo resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(QuestionInfo resources);

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
    void download(List<QuestionInfoDto> all, HttpServletResponse response) throws IOException;

    List<QuestionsVo> findQuestionInfoVosByPid(Integer pid);

    void saveUserWrongQuestion(QuestionReq req) throws  Exception;

    void saveUserFavoriteQuestion(QuestionReq req) throws  Exception;

    int findUserFavoriteQuestion(QuestionReq req);

    long findUserFavoriteQuestionCount(QuestionReq req);

    long findUserWrongQuestionCount(QuestionReq req);

    List<QuestionsVo> findUserFavoriteQuestionInfos(QuestionInfoListReq req);

    List<QuestionsVo> findUserWrongQuestionInfos(QuestionInfoListReq req);

    long findQuestionCount(Integer cid);

    List<QuestionsVo> findShareQuestionByQid(Integer qid);

    String saveUserQuestionCorrect(QuestionCorrectReq req);

    PageResult<QuestionsVo> findQuestionPageInfoByKeywords(SearchQuestionInfoReq req);

    ExportInfo findQuestionExportInfo(ExportInfoReq req);

    String createQuestionPdfFile(Integer pid);

    Integer exportQuestionInfo(ExportInfoReq req,ExportInfo info );

    void handlerExportQuestion();

    TodayQuestionInfo findTodayQuestionInfo(Integer uid);

    void refreshTodayQuestion();

    String saveUserTodayQuestionAnswer(UserAnswerReq req);
}