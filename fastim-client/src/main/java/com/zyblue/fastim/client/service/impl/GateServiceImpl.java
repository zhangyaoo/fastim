package com.zyblue.fastim.client.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zyblue.fastim.client.service.GateService;

import com.zyblue.fastim.common.ServerInfo;
import com.zyblue.fastim.common.request.LoginRequest;
import com.zyblue.fastim.common.request.RegisterRequest;
import com.zyblue.fastim.common.response.LoginResponse;
import com.zyblue.fastim.common.response.RegisterResponse;
import com.zyblue.fastim.common.url.UrlConstant;
import com.zyblue.fastim.common.util.HttpUtil;
import com.zyblue.fastim.common.vo.BaseResponse;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GateServiceImpl implements GateService {
    private final static Logger logger = LoggerFactory.getLogger(GateServiceImpl.class);

    @Value("${gate.http.url}")
    private String baseUrl;

    @Override
    public BaseResponse<RegisterResponse> register(RegisterRequest request) {
        logger.info("register|request:{}", request);

        return null;
    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        logger.info("login|request:{}", request);
        Response response = null;
        try {
            response = HttpUtil.doPost(baseUrl + UrlConstant.GET_SERVERINFO, request);
        }catch (Exception e){
            logger.info("e:{}", e);
        }
        if(null == response){
            return new BaseResponse(500, "failed", null);
        }
        LoginResponse loginResponse = null;
        try {
            String res = response.body().string();
            BaseResponse baseResponse = JSONObject.parseObject(res, BaseResponse.class);
            logger.info("baseResponse:{}", baseResponse);
            loginResponse  = JSONObject.parseObject(baseResponse.getData().toString(), LoginResponse.class);
        }catch (Exception e){
            logger.info("e:{}", e);
        }
        return new BaseResponse(200, "success", loginResponse);
    }
}
