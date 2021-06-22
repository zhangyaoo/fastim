package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.pojo.BaseResponse;
import com.zyblue.fastim.common.pojo.request.LoginRequest;
import com.zyblue.fastim.common.pojo.request.MsgRequest;
import com.zyblue.fastim.common.pojo.request.RegisterRequest;
import com.zyblue.fastim.common.pojo.response.LoginResponse;
import com.zyblue.fastim.common.pojo.response.RegisterResponse;

public interface BizService {
    BaseResponse<RegisterResponse> register(RegisterRequest request);

    BaseResponse<LoginResponse> login(LoginRequest request);

    BaseResponse<?> logout();

    /**
     * c2s：发送client to server“端到云”的消息 如 keepalive
     * c2c：发送client to client“端到端”的消息
     */
    BaseResponse<?> sendMsg(MsgRequest request);

    /**
     * 拉取离线消息
     */
    BaseResponse<?> getOfflineMsg();

    /**
     * 收到消息的callback回调
     */
    BaseResponse<?> onMsgRecieved();
}
