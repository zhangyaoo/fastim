package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 12:44  协议转换
 * 动态路由
 */
@ChannelHandler.Sharable
public class GateDynamicRouteHandler extends SimpleChannelInboundHandler<FastImProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol protocol) throws Exception {
        channelHandlerContext.fireChannelRead(protocol);

    }
}
