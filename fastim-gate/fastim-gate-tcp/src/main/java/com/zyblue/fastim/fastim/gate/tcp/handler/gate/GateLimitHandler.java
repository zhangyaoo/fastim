package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.google.common.util.concurrent.RateLimiter;
import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 11:37
 * <p>
 * 限流处理
 */
public class GateLimitHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final RateLimiter limiter = RateLimiter.create(5);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) throws Exception {
        if (!limiter.tryAcquire()) {
           // channelHandlerContext.writeAndFlush();
        }
        channelHandlerContext.fireChannelRead(protocol);

    }
}
