package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 认证handler
 * 认证通过后可以移除此handler
 */
public class GateAuthHandler extends SimpleChannelInboundHandler<FastImProtocol> {

    private final static Logger logger = LoggerFactory.getLogger(GateAuthHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol protocol) throws Exception {

        channelHandlerContext.fireChannelRead(protocol);
        channelHandlerContext.handler().handlerRemoved(channelHandlerContext);
    }
}
