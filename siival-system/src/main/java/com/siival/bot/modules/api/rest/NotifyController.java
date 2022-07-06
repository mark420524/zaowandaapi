package com.siival.bot.modules.api.rest;

import com.siival.bot.modules.api.req.BaseReq;
import com.siival.bot.modules.api.resp.NotifyInfo;
import com.siival.bot.resp.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark acrossxwall@gmail.com
 * @version 1.0.0
 * @ClassName NotifyController
 * 
 * @Date 2022-02-26 14:10
 */
@RestController
@RequestMapping("/weixin/api/notify")
public class NotifyController {

    @PostMapping("/info")
    public R getUserCurrentNotify(BaseReq req) {
        NotifyInfo ni = new NotifyInfo();
        ni.setId(1);
//        ni.setMessage("由于种种原因，我们已经停止了服务，还是要真诚地感谢您的使用");
        ni.setMessage("你的账号由于xx原因，已封禁");
        ni.setTitle("公告");
        ni.setStopService(0);
        ni.setForbidden(false);
        return R.success();
    }
}
