package com.zyblue.fastim.logic.msg.service;

import com.zyblue.fastim.common.pojo.message.SingleChatNotifyResponse;
import com.zyblue.fastim.common.pojo.message.SingleChatRequest;
import com.zyblue.fastim.common.pojo.message.SingleChatResponse;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author will
 * @date 2021/12/8 17:20
 */
@Service
public class SingleReceivedServiceImpl implements SingleReceivedService {

    @Override
    public SingleChatResponse received(SingleChatRequest request) {
        // 根据接收消息的sequence_id来进行客户端发送消息的去重
        // 生成递增的消息ID，将发送的信息和ID打包一块入库
        // 入库成功后返回ACK，ACK包带上服务端生成的消息ID
        // 检测接收用户是否在线，在线直接推送给用户，离线则不推送，进行push通知
        // 发送消息给router层
        return new SingleChatResponse();
    }

    @Override
    public void receivedAck(SingleChatNotifyResponse response) {
        //接收ACK后，将消息标为已送达
    }
}
