package com.zyblue.fastim.client.service.impl;

import com.zyblue.fastim.client.client.FastImClient;
import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.common.codec.FastImProtocol;
import com.zyblue.fastim.common.enumeration.DataType;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.request.MsgRequest;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/7/12 18:03
 */
@Component
public class GroupChatServiceImpl implements ImService {

    private final static Logger logger = LoggerFactory.getLogger(SingleChatServiceImpl.class);

    @Autowired
    private FastImClient fastImClient;

    @Override
    public void received(FastImProtocol fastImProtocol) {

    }

    @Override
    public void sendMsg(FastImProtocol protocol, MsgRequest request) {
        logger.info("sendMsg|request:{}", request);
        protocol.setVersion(1);
        protocol.setCmd(CmdType.GROUP_CHAT.getVal());
        protocol.setMsgType(MsgType.REQUEST.getVal());
        protocol.setDataType(DataType.TEXT.getVal());
        protocol.setData(ProtoStuffUtils.serialize(request));
        fastImClient.send(protocol);
    }
}
