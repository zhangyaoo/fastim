package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.request.RegisterRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.common.pojo.response.RegisterResponse;
import com.zyblue.fastim.common.pojo.BaseResponse;

public interface GateService {
    BaseResponse<RegisterResponse> register(RegisterRequest request);

    BaseResponse<LoginResponse> login(LoginRequest request);
}
