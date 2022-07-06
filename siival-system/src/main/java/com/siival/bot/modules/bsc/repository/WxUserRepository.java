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

import com.siival.bot.modules.bsc.domain.WxUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;

/**
* @website
* @author mark
* @date 2022-01-16
**/
public interface WxUserRepository extends JpaRepository<WxUser, Integer>, JpaSpecificationExecutor<WxUser> {

    WxUser findFirstByUnionid(String unionid);


    WxUser findFirstByOpenid(String openid);

    @Modifying
    @Query(value = "update WxUser set nickName=?2,avatarUrl=?3,country=?4,gender=?5,language=?6,province=?7,updateTime=?8 where id=?1  "   )
    void updateUserInfoById(Integer uid, String nickName, String avatarUrl,
                            String country, Integer gender, String language, String province,
                            Timestamp updateTime);

}