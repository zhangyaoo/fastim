package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.codec.FastImMsg;

/**
 * @author will
 * @date 2021/7/13 9:36
 */
public interface ImService {
    /**
     * 接收消息处理
     * @param fastImMsg fastImMsg
     */
    void received(FastImMsg fastImMsg);

    /**
     * 发送消息
     * @param protocol protocol
     */
    void sendMsg(FastImMsg protocol);
}
