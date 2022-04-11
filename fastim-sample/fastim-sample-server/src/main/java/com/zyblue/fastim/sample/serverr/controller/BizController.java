package com.zyblue.fastim.sample.serverr.controller;

import com.zyblue.fastim.logic.service.PushService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author will
 * @date 2021/11/19 15:50
 */
@RestController
public class BizController {
    @Resource
    private PushService pushService;

    @PostMapping("/fastim-sample/push-test")
    public void serverInfo(String msg) throws Exception {
        pushService.pushAll(msg);
    }
}
