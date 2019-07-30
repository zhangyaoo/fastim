package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.request.LoginRequest;
import com.zyblue.fastim.common.request.RegisterRequest;
import com.zyblue.fastim.common.response.LoginResponse;
import com.zyblue.fastim.common.response.RegisterResponse;
import com.zyblue.fastim.common.vo.BaseResponse;

public interface GateService {
    BaseResponse<RegisterResponse> register(RegisterRequest request);

    BaseResponse<LoginResponse> login(LoginRequest request);
}
