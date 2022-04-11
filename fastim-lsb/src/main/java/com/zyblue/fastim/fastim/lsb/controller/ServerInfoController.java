package com.zyblue.fastim.fastim.lsb.controller;

import com.zyblue.fastim.common.pojo.ServerInfo;
import com.zyblue.fastim.fastim.lsb.service.ServerInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author will
 * @date 2021/11/30 14:32
 */
@RestController
public class ServerInfoController {

    @Resource
    private ServerInfoService serverInfoService;

    @GetMapping("/fastim-lsb/server-info")
    public ServerInfo serverInfo() throws Exception {
        return serverInfoService.getServerInfo();
    }
}
