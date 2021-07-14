package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 11:44
 *
 * service全链路超时 熔断
 */
@ChannelHandler.Sharable
public class GateServiceTimeoutHandler extends SimpleChannelInboundHandler<FastImProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol protocol) throws Exception {

    }
}

