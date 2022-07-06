package com.siival.bot.modules.api.rest;


import com.siival.bot.modules.api.resp.CategoryInfo;
import com.siival.bot.modules.bsc.service.ExamMenuService;
import com.siival.bot.modules.bsc.service.QuestionMenuService;
import com.siival.bot.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName CategoryController
 * @Description 查询答题的系统分类
 * @Date 2022/1/17 14:46
 */
@RestController
@RequestMapping("/weixin/api/category")
public class CategoryController {

    @Autowired
    public QuestionMenuService questionMenuService;
    @Autowired
    private ExamMenuService examMenuService;

    @GetMapping("/list")
    public R getQuestionCategory(Integer pid) {
        List<CategoryInfo> list = questionMenuService.getEnableMenuInfo(pid);
        return R.success(list);
    }

    @GetMapping("/exam/list")
    public R getExamCategory(Integer pid) {
        List<CategoryInfo> list = examMenuService.getEnableMenuInfo(pid);
        return R.success(list);
    }
}
