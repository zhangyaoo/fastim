package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.codec.FastImProtocol;
import com.zyblue.fastim.common.pojo.request.MsgRequest;

/**
 * @author will
 * @date 2021/7/13 9:36
 */
public interface ImService {
    void received(FastImProtocol fastImProtocol);

    void sendMsg(FastImProtocol protocol, MsgRequest request);
}
