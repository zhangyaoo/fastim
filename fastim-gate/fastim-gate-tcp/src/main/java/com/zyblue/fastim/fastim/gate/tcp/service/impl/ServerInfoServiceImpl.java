package com.zyblue.fastim.fastim.gate.tcp.service.impl;

import com.zyblue.fastim.common.ServerInfo;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.fastim.gate.tcp.service.ServerInfoService;
import org.springframework.stereotype.Service;

@Service
public class ServerInfoServiceImpl implements ServerInfoService {

    public static final Integer EXPIRED_TIME = 30;

    /*@Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;*/

    @Override
    public LoginResponse getServerInfo(LoginRequest request) {
        ServerInfo info = new ServerInfo();
        info.setIp("127.0.0.1");
        info.setServerPort(9527);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("zyblue");
        loginResponse.setServerInfo(info);
        //redisTemplate.opsForValue().set(RedisKey.FASTIM_TOKEN + 9527, "zyblue", EXPIRED_TIME, TimeUnit.MINUTES);
        return loginResponse;
    }
}
