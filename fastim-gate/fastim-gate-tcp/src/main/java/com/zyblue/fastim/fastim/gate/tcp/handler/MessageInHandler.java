package com.zyblue.fastim.fastim.gate.tcp.handler;

import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2022/4/12 16:10
 * 来自MSG server的消息，不需要关注是哪个channel，只做转发
 */
public class MessageInHandler extends SimpleChannelInboundHandler<FastImMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FastImMsg msg) throws Exception {
        // 收到msg服务发来的消息
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 收到msg服务发来的建立连接请求
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 收到msg服务发来的取消建立连接请求
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 心跳
    }
}
