package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.client.FastImClient;
import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.manager.MsgManager;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.message.MsgRequest;
import com.zyblue.fastim.common.pojo.message.SingleChatRequest;
import com.zyblue.fastim.common.pojo.message.SingleChatResponse;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author will
 * @date 2021/7/12 18:02
 */
public class SingleChatServiceImpl implements ImService<SingleChatRequest> {
    private final static Logger logger = LoggerFactory.getLogger(SingleChatServiceImpl.class);

    private static final SingleChatServiceImpl singleChatService = new SingleChatServiceImpl();

    public static ImService<?> getInstance() {
        return singleChatService;
    }

    @Override
    public void received(FastImMsg fastImMsg) {
        if (fastImMsg.getMsgType() == MsgType.ACK.getVal()) {
            SingleChatResponse response = ProtoStuffUtils.deserialize(fastImMsg.getData(), SingleChatResponse.class);
            int sequenceId = fastImMsg.getSequenceId();
            Timeout timeout = MsgManager.ACK_MSG_TIMEOUT_LIST.remove(sequenceId);
            timeout.cancel();
            MsgRequest msgRequest = MsgManager.MSG_REQUEST_LIST.remove(sequenceId);
            logger.info("消息发送成功, msgId:{}, sequenceId:{}, msgRequest:{}", response.getMsgId(), sequenceId, msgRequest);
        } else if (fastImMsg.getMsgType() == MsgType.NOTIFY.getVal()) {
            /*
             * 单聊消息为了保证实时性，服务器直接推算过来
             */
            SingleChatRequest request = ProtoStuffUtils.deserialize(fastImMsg.getData(), SingleChatRequest.class);


        } else {
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
        FastImClient.getInstance().send(protocol, request);
    }
}
