package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.nettyclient.FastImClient;
import com.zyblue.fastim.client.service.BizService;
import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.request.MsgRequest;
import com.zyblue.fastim.common.pojo.request.RegisterRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.common.pojo.response.RegisterResponse;
import com.zyblue.fastim.common.url.UrlConstant;
import com.zyblue.fastim.common.util.HttpUtil;
import com.zyblue.fastim.common.util.JacksonUtils;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class BizServiceImpl implements BizService {
    private final static Logger logger = LoggerFactory.getLogger(BizServiceImpl.class);

    @Value("${gate.url}")
    private String gateUrl;

    @Value("${gate.port}")
    private Integer gatePort;

    @Autowired
    private FastImClient client;

    @Override
    public BaseResponse<RegisterResponse> register(RegisterRequest request) {
        logger.info("register|request:{}", request);

        RegisterResponse registerResponse = null;
        try {
            String body = HttpUtil.doPost(gateUrl + ":" + gatePort + UrlConstant.Gate.REGISTER, request);
            BaseResponse<?> baseResponse = JacksonUtils.json2pojo(body, BaseResponse.class);
            logger.info("baseResponse:{}", baseResponse);
            registerResponse  = JacksonUtils.json2pojo(baseResponse.getData().toString(), RegisterResponse.class);
        }catch (Exception e){
            logger.error("error:", e);
        }
        return new BaseResponse<>(200, "success", registerResponse);
    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        logger.info("login|request:{}", request);

        LoginResponse loginResponse;
        try {
            String body = HttpUtil.doPost(gateUrl + ":" + gatePort + UrlConstant.Gate.LOGIN, request);
            BaseResponse<?> baseResponse = JacksonUtils.json2pojo(body, BaseResponse.class);
            logger.info("baseResponse:{}", baseResponse);
            loginResponse  = JacksonUtils.json2pojo(baseResponse.getData().toString(), LoginResponse.class);
        }catch (Exception e){
            logger.error("error:", e);
            return new BaseResponse<>(500, "failed", null);
        }
        return new BaseResponse<>(200, "success", loginResponse);
    }

    /**
     * 使用测试
     */
    @Override
    public BaseResponse<?> sendMsgTest(MsgRequest request) {
        logger.info("sendMsgTest|request:{}", request);

        // (1) 尝试socket发送
        ChannelFuture future = client.send(request);
        future.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                BaseResponse<?> response = JacksonUtils.obj2pojo(future.get(), BaseResponse.class);
                logger.info("response:{}", response);
            }
        });

        // (2) 尝试http发送
        BaseResponse<?> response;
        try {
            String body = HttpUtil.doPost(gateUrl + ":" + gatePort + UrlConstant.Biz.MSG1, request);
            response = JacksonUtils.json2pojo(body, BaseResponse.class);
            logger.info("response:{}", response);
        }catch (Exception e){
            logger.error("error:", e);
            return new BaseResponse<>(500, "failed", null);
        }
        return new BaseResponse<>(200, "success", response);
    }
}
