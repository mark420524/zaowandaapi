package com.siival.bot.modules.api.req;

import com.siival.bot.annotation.Query;
import lombok.Data;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName SearchExamReq
 * 
 * @Date 2022-03-30 20:53
 */
@Data
public class SearchExamReq extends BaseReq {
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String examName;
    @Query
    private String version;
    @Query
    private String fileType;

    /** 精确 */
    @Query
    private Integer status;

    @Query
    private Integer pid;
}
