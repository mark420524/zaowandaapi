package com.siival.bot.resp;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName PageResult
 * 
 * @Date 2022-03-01 10:10
 */
@Data
public class PageResult<T> {

    private Integer page;
    private Integer size;
    private long totalPages;
    private long totalSize;
    private List<T> list;

    public PageResult() {
    }

    public PageResult(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

}
