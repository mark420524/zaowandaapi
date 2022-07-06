package com.siival.bot.modules.api.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UploadFileReq
 * @Description
 * @Date 2022-05-13 15:51
 */
@Data
public class UploadFileReq extends BaseReq {

    private MultipartFile file;
    private String password;
    //1 -只读
    private Integer readonly;
    // 1-是免费操作，2-消耗积分
    private Integer type;

    private String waterMark;
    private String fileName;
    private String email;

}
