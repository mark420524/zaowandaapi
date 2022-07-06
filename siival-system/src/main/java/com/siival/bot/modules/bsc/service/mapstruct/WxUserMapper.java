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
package com.siival.bot.modules.bsc.service.mapstruct;


import com.siival.bot.base.BaseMapper;
import com.siival.bot.modules.bsc.domain.WxUser;
import com.siival.bot.modules.bsc.service.dto.WxUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @website
* @author mark
* @date 2022-01-16
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WxUserMapper extends BaseMapper<WxUserDto, WxUser> {

}