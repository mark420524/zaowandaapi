package com.siival.bot.modules.bsc.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ModifyUserIntegralReq
 * 
 * @Date 2022-03-22 17:29
 */
@Data
public class ModifyUserIntegralReq {

    private Integer id;
    private String nickName;
    private Integer integral;
    private Integer status;
    private String remark;

}
