package com.zyblue.fastim.fastim.gate.http.handler;

import com.zyblue.fastim.fastim.gate.http.plugin.chain.HttpHandlerChain;
import com.zyblue.fastim.fastim.gate.http.util.SpringContextHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpRequest;

/**
 * @author will
 * @date 2021/6/7 23:01
 */
@ChannelHandler.Sharable
public class HttpHandler extends SimpleChannelInboundHandler<DefaultHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest httpRequest) throws Exception {
        HttpHandlerChain chain = SpringContextHolder.getBean(HttpHandlerChain.class);
        /*
         * 责任链不进行IO操作
         * 1、可以利用netty本身责任链的实现
         * 2、可以利用自己实现的一个自定义责任链
         */
        chain.process(channelHandlerContext, httpRequest);
    }
}
