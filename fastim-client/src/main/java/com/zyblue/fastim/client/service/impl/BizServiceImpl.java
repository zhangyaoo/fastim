package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.service.BizService;
import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.request.LogoutRequest;
import com.zyblue.fastim.common.pojo.request.RegisterRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.common.pojo.response.LogoutResponse;
import com.zyblue.fastim.common.pojo.response.RegisterResponse;
import com.zyblue.fastim.common.constant.UrlConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BizServiceImpl implements BizService{
    private final static Logger logger = LoggerFactory.getLogger(BizServiceImpl.class);

    @Value("${http.gate.url}")
    private String gateUrl;

    @Value("${http.gate.port}")
    private Integer gatePort;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BaseResponse<?> register(RegisterRequest request) {
        logger.info("register|request:{}", request);

        RegisterResponse registerResponse;
        try {
            registerResponse = restTemplate.postForObject(gateUrl + ":" + gatePort + UrlConstant.Gate.REGISTER, request, RegisterResponse.class);
        }catch (Exception e){
            logger.error("error:", e);
            return new BaseResponse<>(500, "failed", null);
        }
        return new BaseResponse<>(200, "success", registerResponse);
    }

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
        return new BaseResponse<>(200, "success", loginResponse);
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
        return new BaseResponse<>(200, "success", logoutResponse);
    }
}
