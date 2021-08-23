package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.client.FastImClient;
import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.manager.MsgManager;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.message.SingleChatRequest;
import com.zyblue.fastim.common.pojo.message.SingleChatResponse;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/7/12 18:02
 */
@Component
public class SingleChatServiceImpl implements ImService<SingleChatRequest> {

    private final static Logger logger = LoggerFactory.getLogger(SingleChatServiceImpl.class);

    @Autowired
    private FastImClient fastImClient;

    @Override
    public void received(FastImMsg fastImMsg) {
        SingleChatResponse response = ProtoStuffUtils.deserialize(fastImMsg.getData(), SingleChatResponse.class);
        if(fastImMsg.getMsgType() == MsgType.ACK.getVal()){
            int sequenceId = fastImMsg.getSequenceId();
            Timeout timeout = MsgManager.ACK_MSG_LIST.remove(sequenceId);
            timeout.cancel();
            // TODO 回调前端发送消息成功
        }else if(fastImMsg.getMsgType() == MsgType.NOTIFY.getVal()){

        }else {
            logger.info("received request packet, ignored");
        }
    }

    @Override
    public void sendMsg(FastImMsg protocol, SingleChatRequest request) {
        logger.info("sendMsg|request:{}", request);
        protocol.setVersion(1);
        protocol.setCmd(CmdType.SINGLE_CHAT.getVal());
        protocol.setMsgType(MsgType.REQUEST.getVal());
        protocol.setData(ProtoStuffUtils.serialize(request));
        fastImClient.send(protocol);
    }
}
