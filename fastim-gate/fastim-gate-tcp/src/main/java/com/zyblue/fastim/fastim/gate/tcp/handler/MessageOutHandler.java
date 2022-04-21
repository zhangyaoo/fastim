package com.zyblue.fastim.fastim.gate.tcp.handler;

import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2022/4/12 16:10
 */
public class MessageOutHandler extends SimpleChannelInboundHandler<FastImMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FastImMsg msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 定时检测客户端存活状态，服务端如果没有及时关闭socket，就会导致处于close_wait状态的链路过多，影响新的客户端无法接入
        ctx.channel().close();
    }
}
