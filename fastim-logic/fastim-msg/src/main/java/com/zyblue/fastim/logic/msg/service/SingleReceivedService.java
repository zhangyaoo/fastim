package com.zyblue.fastim.logic.msg.service;

import com.zyblue.fastim.common.pojo.message.SingleChatNotifyResponse;
import com.zyblue.fastim.common.pojo.message.SingleChatRequest;
import com.zyblue.fastim.common.pojo.message.SingleChatResponse;

/**
 * @author will
 * @date 2021/12/8 17:20
 */
public interface SingleReceivedService {

    /**
     * 接收消息处理
     * @param request request
     * @return  res
     */
    SingleChatResponse received(SingleChatRequest request);

    /**
     * 接收消息处理
     * @param response response
     */
    void receivedAck(SingleChatNotifyResponse response);
}
