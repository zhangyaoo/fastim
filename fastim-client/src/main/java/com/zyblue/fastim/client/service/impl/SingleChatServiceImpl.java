package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.ClientManager;
import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.event.EventBusCenter;
import com.zyblue.fastim.client.event.NewMsgEvent;
import com.zyblue.fastim.client.msg.MsgManager;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.message.SingleChatNotify;
import com.zyblue.fastim.common.pojo.message.SingleChatResponse;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author will
 * @date 2021/7/12 18:02
 */
public class SingleChatServiceImpl implements ImService {
    private final static Logger logger = LoggerFactory.getLogger(SingleChatServiceImpl.class);

    private static final SingleChatServiceImpl singleChatService = new SingleChatServiceImpl();

    public static ImService getInstance() {
        return singleChatService;
    }

    @Override
    public void received(FastImMsg fastImMsg) {
        if (fastImMsg.getMsgType() == MsgType.ACK.getVal()) {
            /*
             * 单聊消息ACK，去掉定时任务消息
             */
            SingleChatResponse response = ProtoStuffUtils.deserialize(fastImMsg.getData(), SingleChatResponse.class);
            int sequenceId = fastImMsg.getSequenceId();
            Timeout timeout = MsgManager.ACK_MSG_TIMEOUT_LIST.remove(sequenceId);
            timeout.cancel();
            logger.info("消息发送成功, msgId:{}, sequenceId:{}", response.getMsgId(), sequenceId);
            MsgManager.SESSION_MSG_ID_LIST.put(response.getSessionId(), response.getMsgId());
        } else if (fastImMsg.getMsgType() == MsgType.NOTIFY.getVal()) {
            /*
             * 单聊消息为了保证实时性，服务器直接推算过来
             */
            SingleChatNotify notify = ProtoStuffUtils.deserialize(fastImMsg.getData(), SingleChatNotify.class);
            Long lastMsgId = MsgManager.SESSION_MSG_ID_LIST.get(notify.getSessionId());
            logger.info("received msg:{}", notify);
            if(lastMsgId > notify.getMsgId()){
                logger.info("received request msgId < lastMsgId");
                return;
            }
            if(lastMsgId.equals(notify.getMsgId())){
                // 保持幂等
                logger.info("received request msgId = lastMsgId");
            }else {
                EventBusCenter.post(new NewMsgEvent(fastImMsg, notify));
            }
            fastImMsg.setMsgType(MsgType.ACK.getVal());
            fastImMsg.setData(new byte[0]);
            ClientManager.getInstance().send(fastImMsg);
            MsgManager.SESSION_MSG_ID_LIST.put(notify.getSessionId(), notify.getMsgId());

        } else {
            logger.info("received request packet, ignored");
        }
    }

    @Override
    public void sendMsg(FastImMsg msg) {
        logger.info("sendMsg|msg:{}", msg);
        msg.setVersion(1);
        msg.setCmd(CmdType.SINGLE_CHAT.getVal());
        msg.setMsgType(MsgType.REQUEST.getVal());
        ClientManager.getInstance().send(msg);
    }
}
