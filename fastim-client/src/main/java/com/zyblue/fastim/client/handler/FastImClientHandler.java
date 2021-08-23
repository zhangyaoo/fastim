package com.zyblue.fastim.client.handler;

import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.client.util.SpringBeanFactory;
import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // TODO 组提交优化 责任链优化
        ImService service = (ImService) SpringBeanFactory.getBean(cmdType.getClazz());
        service.received(fastImMsg);
    }
}
