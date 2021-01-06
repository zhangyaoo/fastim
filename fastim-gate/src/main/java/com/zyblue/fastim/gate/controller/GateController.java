package com.zyblue.fastim.gate.controller;


import com.zyblue.fastim.common.ServerInfo;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.common.url.UrlConstant;
import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.gate.service.ServerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class GateController {
    private final static Logger logger = LoggerFactory.getLogger(GateController.class);

    @Autowired
    private ServerInfoService serverInfoService;

    @RequestMapping(value = UrlConstant.Gate.GET_SERVERINFO, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse<ServerInfo> getServerInfo(@RequestBody LoginRequest loginRequest){
        logger.info("getServerInfo|loginRequest:{}", loginRequest);
        LoginResponse loginResponse = serverInfoService.getServerInfo(loginRequest);
        logger.info("loginResponse:{}", loginResponse);
        return new BaseResponse(200, "success", loginResponse);
    }
}
