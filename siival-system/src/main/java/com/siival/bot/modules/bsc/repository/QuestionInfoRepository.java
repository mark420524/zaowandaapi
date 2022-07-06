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
package com.siival.bot.modules.bsc.repository;

import com.siival.bot.modules.bsc.domain.QuestionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @website
* @author mark
* @date 2022-02-12
**/
public interface QuestionInfoRepository extends JpaRepository<QuestionInfo, Integer>, JpaSpecificationExecutor<QuestionInfo> {

    List<QuestionInfo> findByStatusAndPidAndTypeOrderByIdAsc(Integer status, Integer pid,Integer type);

    @Query(value = "select qi  from QuestionInfo qi inner join UserFavoriteQuestion ufq on qi.id=ufq.questionId where" +
            " ufq.pid=?1 and ufq.uid=?2 ")
    List<QuestionInfo> findUserFavoriteQuestion(Integer cid,Integer uid);

    @Query(value = "select qi  from QuestionInfo qi inner join UserWrongQuestion uwq on qi.id=uwq.questionId where" +
            " uwq.pid=?1 and uwq.uid=?2 ")
    List<QuestionInfo> findUserWrongQuestion(Integer cid,Integer uid);

    long countByPidAndStatusAndType(Integer pid,Integer status,Integer type);

    long countByType(Integer type);

    Page<QuestionInfo> findByStatusAndPidAndTypeAndQuestionContaining(Integer status, Integer pid, Integer type,
                                                                      String question, Pageable pageable);

    @Query(value = "select min(id),max(id),count(1) from QuestionInfo where type=?1")
    Object[] findFirstMinAndMaxByType(Integer type);

    QuestionInfo findFirstByIdGreaterThanEqualAndType(Integer id, Integer type);

    QuestionInfo findFirstByIdLessThanEqualAndType(Integer id, Integer type);

}