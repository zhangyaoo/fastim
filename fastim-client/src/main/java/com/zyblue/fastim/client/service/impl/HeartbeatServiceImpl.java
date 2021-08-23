package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.pojo.message.KeepAliveRequest;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/7/12 18:01
 */
@Component
public class HeartbeatServiceImpl implements ImService<KeepAliveRequest> {

    @Override
    public void received(FastImMsg fastImMsg) {

    }

    @Override
    public void sendMsg(FastImMsg protocol, KeepAliveRequest request) {

    }
}
