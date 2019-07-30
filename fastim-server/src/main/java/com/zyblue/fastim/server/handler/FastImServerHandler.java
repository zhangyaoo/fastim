package com.zyblue.fastim.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.api.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class FastImServerHandler extends SimpleChannelInboundHandler {

    private final static Logger logger = LoggerFactory.getLogger(FastImServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("ImServer recieve msg");
        ByteBuf data = (ByteBuf) o;

        logger.info("ImServer recieve msg:{}", data.toString(Charset.forName("utf-8")));

        byte[] bytes = "hello world from server".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        buffer.writeBytes(bytes);
        channelHandlerContext.channel().writeAndFlush(buffer);
    }

    private JSONObject decode(ByteBuf byteBuf){
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);
        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        return JSONObject.parseObject(bytes, JSONObject.class);
    }
}
