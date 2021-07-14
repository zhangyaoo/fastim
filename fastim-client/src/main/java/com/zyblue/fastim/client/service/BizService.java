package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.request.LogoutRequest;
import com.zyblue.fastim.common.pojo.request.RegisterRequest;

/**
 * @author will
 * @date 2021/7/13 10:46
 */
public interface BizService {

     BaseResponse<?> register(RegisterRequest request);

     BaseResponse<?> login(LoginRequest request);

     BaseResponse<?> logout(LogoutRequest request);
}
