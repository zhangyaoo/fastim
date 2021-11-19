package com.zyblue.fastim.sample.service;


import com.zyblue.fastim.sample.pojo.BaseResponse;
import com.zyblue.fastim.sample.pojo.request.LoginRequest;
import com.zyblue.fastim.sample.pojo.request.LogoutRequest;

/**
 * @author will
 * @date 2021/7/13 10:46
 */
public interface BizService {

     BaseResponse<?> login(LoginRequest request);

     BaseResponse<?> logout(LogoutRequest request);
}
