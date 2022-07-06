package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName QuestionsVo
 * 
 * @Date 2022-02-12 17:23
 */
@Data
public class QuestionsVo {

    private Integer id;
    private Integer type;
    private Integer multiply;
    private String rightAnswer;
    private String analysis;
    private String question;
    private List<SelectListInfo> selectList;
}
