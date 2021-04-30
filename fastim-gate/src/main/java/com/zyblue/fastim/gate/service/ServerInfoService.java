package com.zyblue.fastim.gate.service;

import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;

public interface ServerInfoService {
    LoginResponse getServerInfo(LoginRequest request);
}
