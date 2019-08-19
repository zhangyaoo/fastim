package com.zyblue.fastim.gate.service;

import com.zyblue.fastim.common.request.LoginRequest;
import com.zyblue.fastim.common.response.LoginResponse;

public interface ServerInfoService {
    LoginResponse getServerInfo(LoginRequest request);
}
