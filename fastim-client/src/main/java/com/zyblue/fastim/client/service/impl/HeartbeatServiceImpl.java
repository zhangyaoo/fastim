package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImProtocol;
import com.zyblue.fastim.common.pojo.request.MsgRequest;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/7/12 18:01
 */
@Component
public class HeartbeatServiceImpl implements ImService {

    @Override
    public void received(FastImProtocol fastImProtocol) {

    }

    @Override
    public void sendMsg(FastImProtocol protocol, MsgRequest request) {

    }
}
