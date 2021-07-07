package com.zyblue.fastim.fastim.gate.tcp.handler;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FastImServerHandler extends SimpleChannelInboundHandler<FastImProtocol> {

    private final static Logger logger = LoggerFactory.getLogger(FastImServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol fastImProtocol) throws Exception {
        // TODO 协议转换给后端
    }
}
