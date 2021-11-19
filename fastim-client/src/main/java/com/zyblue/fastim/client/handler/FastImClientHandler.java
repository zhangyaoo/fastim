package com.zyblue.fastim.client.handler;

import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.service.impl.GroupChatServiceImpl;
import com.zyblue.fastim.client.service.impl.HeartbeatServiceImpl;
import com.zyblue.fastim.client.service.impl.PushServiceImpl;
import com.zyblue.fastim.client.service.impl.SingleChatServiceImpl;
import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端处理
 * @author will
 */
public class FastImClientHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final static Logger logger = LoggerFactory.getLogger(FastImClientHandler.class);


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg fastImMsg) throws Exception {
        CmdType cmdType = CmdType.getCmdTypeByVal(fastImMsg.getCmd());

        if(cmdType == null){
            logger.error("param error: cmd");
            throw new RuntimeException("param error: cmd");
        }

        switch (cmdType){
            case SINGLE_CHAT:
                SingleChatServiceImpl.getInstance().received(fastImMsg);break;
            case GROUP_CHAT:
                GroupChatServiceImpl.getInstance().received(fastImMsg);break;
            case HEARTBEAT:
                HeartbeatServiceImpl.getInstance().received(fastImMsg);break;
            case PUSH:
                PushServiceImpl.getInstance().received(fastImMsg);break;
            default:break;
        }
    }
}
