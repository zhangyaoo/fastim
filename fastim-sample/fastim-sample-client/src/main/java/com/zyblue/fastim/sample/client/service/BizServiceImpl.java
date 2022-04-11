package com.zyblue.fastim.sample.client.service;

import com.zyblue.fastim.sample.client.constant.UrlConstant;
import com.zyblue.fastim.sample.client.manager.ConnectionManager;
import com.zyblue.fastim.sample.client.pojo.BaseResponse;
import com.zyblue.fastim.sample.client.pojo.request.LoginRequest;
import com.zyblue.fastim.sample.client.pojo.request.LogoutRequest;
import com.zyblue.fastim.sample.client.pojo.response.LoginResponse;
import com.zyblue.fastim.sample.client.pojo.response.LogoutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class BizServiceImpl implements BizService{
    private final static Logger logger = LoggerFactory.getLogger(BizServiceImpl.class);


    @Value("${http.gate.url}")
    private String gateUrl;

    @Value("${http.gate.port}")
    private Integer gatePort;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ConnectionManager connectionManager;

    @Override
    public BaseResponse<?> login(LoginRequest request) {
        logger.info("login|request:{}", request);

        LoginResponse loginResponse;
        try {
            loginResponse = restTemplate.postForObject(gateUrl + ":" + gatePort + UrlConstant.Gate.LOGIN, request, LoginResponse.class);
        }catch (Exception e){
            logger.error("error:", e);
            return new BaseResponse<>(500, "failed", null);
        }

        if(loginResponse != null){
            // 登录后进行TCP连接
            boolean connect = connectionManager.connect(loginResponse.getTcpUrl(), loginResponse.getTcpPort());
            if(connect){
                logger.info("connect success");
            }
            return new BaseResponse<>(200, "success", loginResponse);
        }else {
            return new BaseResponse<>(500, "failed", loginResponse);
        }
    }

    @Override
    public BaseResponse<?> logout(LogoutRequest request) {
        logger.info("login|logout:{}", request);

        LogoutResponse logoutResponse;
        try {
            logoutResponse = restTemplate.postForObject(gateUrl + ":" + gatePort + UrlConstant.Gate.LOGOUT, request, LogoutResponse.class);
        }catch (Exception e){
            logger.error("error:", e);
            return new BaseResponse<>(500, "failed", null);
        }
        if(logoutResponse != null){
            // 登出后取消TCP连接
            connectionManager.disConnect();
            return new BaseResponse<>(200, "success", logoutResponse);
        }else {
            return new BaseResponse<>(500, "failed", logoutResponse);
        }
    }
}
