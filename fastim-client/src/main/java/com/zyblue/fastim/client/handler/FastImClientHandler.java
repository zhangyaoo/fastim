package com.zyblue.fastim.client.handler;

import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.service.ImService;
import com.zyblue.fastim.client.util.SpringBeanFactory;
import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastImClientHandler extends SimpleChannelInboundHandler<FastImProtocol> {

    private final static Logger logger = LoggerFactory.getLogger(FastImClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol fastImProtocol) throws Exception {
        CmdType cmdType = CmdType.getCmdTypeByVal(fastImProtocol.getCmd());

        if(cmdType == null){
            logger.error("param error: cmd");
            throw new RuntimeException("param error: cmd");
        }

        ImService service = (ImService) SpringBeanFactory.getBean(cmdType.getClazz());
        service.received(fastImProtocol);
    }
}
