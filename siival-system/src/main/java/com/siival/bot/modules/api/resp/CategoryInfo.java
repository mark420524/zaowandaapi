package com.siival.bot.modules.api.resp;

import lombok.Data;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName CategoryInfo
 * @Description
 * @Date 2022/1/17 14:48
 */
@Data
public class CategoryInfo {

    private String name;
    private Integer id;

    private List<CategoryInfo> children;
}
