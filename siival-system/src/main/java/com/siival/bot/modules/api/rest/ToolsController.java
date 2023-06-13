package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.bsc.service.AppToolsService;
import com.siival.bot.modules.bsc.service.ToolItemsService;
import com.siival.bot.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixin/api/tools")
public class ToolsController {

    @Autowired
    private ToolItemsService toolItemsService;
    @Autowired
    private AppToolsService appToolsService;

    @GetMapping("/item")
    public R getToolsItems() {
        return R.success(toolItemsService.findToolsItemInfo());
    }

    @GetMapping("/other")
    public R getToolsOtherApp() {
        return R.success(appToolsService.findAppToolsItem());
    }
}