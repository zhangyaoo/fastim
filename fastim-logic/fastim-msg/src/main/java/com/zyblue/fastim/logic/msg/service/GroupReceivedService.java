package com.zyblue.fastim.logic.msg.service;

import com.zyblue.fastim.common.pojo.message.GroupChatNotifyResponse;
import com.zyblue.fastim.common.pojo.message.GroupChatRequest;
import com.zyblue.fastim.common.pojo.message.GroupChatResponse;

/**
 * @author will
 * @date 2021/12/27 10:39
 */
public interface GroupReceivedService {
    /**
     * 接收消息处理
     * @param request request
     * @return  res
     */
    GroupChatResponse received(GroupChatRequest request);

    /**
     * 接收消息处理
     * @param response response
     */
    void receivedAck(GroupChatNotifyResponse response);
}
