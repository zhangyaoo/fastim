package com.zyblue.fastim.gate.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 *
 */
public class FastImServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(FastImServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("ImServer channel active");
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("ImServer recieve msg");
        ByteBuf data = (ByteBuf) o;

        logger.info("ImServer recieve msg:{}", data.toString(Charset.forName("utf-8")));

        byte[] bytes = "hello world from server".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        buffer.writeBytes(bytes);
        channelHandlerContext.channel().writeAndFlush(buffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info("===>exceptionCaught");
        logger.error(cause.getMessage(), cause);
    }
}
