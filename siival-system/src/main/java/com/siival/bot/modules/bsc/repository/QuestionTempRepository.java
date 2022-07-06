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

import com.siival.bot.modules.bsc.domain.QuestionTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


/**
* @website
* @author mark
* @date 2022-02-12
**/
public interface QuestionTempRepository extends JpaRepository<QuestionTemp, Integer>, JpaSpecificationExecutor<QuestionTemp> {

    @Query(value = "select qi  from QuestionTemp qi  where" +
            " qi.status=?1 and qi.options is not null and qi.options!='' ")
    Page<QuestionTemp> findTempQuestion(Integer status, Pageable pageable);

    @Modifying
    @Query(value = "update QuestionTemp set status=?1 where id=?2")
    void updateQuestionTempStatus(Integer status,Integer id);


    @Query(value = "select qi  from QuestionTemp qi  where" +
            " qi.status=?1 and qi.options='' and qi.question!='' and qi.createTime>?2 ")
    Page<QuestionTemp> findTempQuestionBuildOptions(Integer status, Timestamp createTime, Pageable pageable);
}