package com.zyblue.fastim.client.service;

import com.zyblue.fastim.common.codec.FastImMsg;

/**
 * @author will
 * @date 2021/7/13 9:36
 */
public interface ImService<T> {
    void received(FastImMsg fastImMsg);

    void sendMsg(FastImMsg protocol, T request);
}
