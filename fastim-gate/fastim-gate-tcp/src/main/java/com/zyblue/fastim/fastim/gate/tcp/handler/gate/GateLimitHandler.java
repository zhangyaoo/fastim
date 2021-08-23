package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 11:37
 *
 * 限流处理
 */
@ChannelHandler.Sharable
public class GateLimitHandler extends SimpleChannelInboundHandler<FastImMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) throws Exception {
        channelHandlerContext.fireChannelRead(protocol);

    }
}
