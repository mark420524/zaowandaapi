package com.siival.bot.modules.api.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName UserInfo
 * 
 * @Date 2022-02-13 13:22
 */
@Data
public class UserInfo  extends BaseReq {

     private String nickName = "";
     private Integer gender = 0;
     private String language = "";
     private String city = "";
     private String province = "";
     private String country = "";
     private String avatarUrl = "";
     private MultipartFile file;
}
