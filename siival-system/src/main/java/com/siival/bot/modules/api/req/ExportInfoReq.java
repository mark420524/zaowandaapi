package com.siival.bot.modules.api.req;

import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName ExportInfoReq
 * 
 * @Date 2022-03-04 10:33
 */
@Data
public class ExportInfoReq extends BaseReq {
    private Integer cid;
    private String email;
    private Integer eid;
}
