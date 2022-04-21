package com.zyblue.fastim.logic.msg.service;

import com.zyblue.fastim.common.pojo.message.GroupChatNotifyResponse;
import com.zyblue.fastim.common.pojo.message.GroupChatRequest;
import com.zyblue.fastim.common.pojo.message.GroupChatResponse;

/**
 * @author will
 * @date 2021/12/27 10:47
 */
public class GroupReceivedServiceImpl implements GroupReceivedService{

    @Override
    public GroupChatResponse received(GroupChatRequest request) {
        // 查询群所有的成员的在线状态。离线和在线成员分不同的方式处理
        // 超过200人的群，进行推拉结合，否则直接推送
        // 直接推送逻辑和单聊差不多。如果超200人，在线并行发送拉取通知，等待在线成员过来拉取，发送拉取通知包如丢失会有兜底机制
        // 会带上这个群标识和上一次拉取群的最小消息ID以及会话ID，服务端会找比这个消息ID大的所有的数据返回给客户端，等待客户端ACK
        // 一段时间没ack继续推送。如果重试几次后没有回ack，那么关闭连接和清除ack等待队列消息
        return null;
    }

    @Override
    public void receivedAck(GroupChatNotifyResponse response) {

    }
}
