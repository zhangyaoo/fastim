package com.zyblue.fastim.client.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class FastImClientHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(FastImClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        logger.info("client connect success");
        byte[] bytes = "hello world from client".getBytes(Charset.forName("utf-8"));
        logger.info("bytes size:{}", bytes.length);
        ByteBuf buffer = channelHandlerContext.alloc().buffer(100,200);
        print(buffer);
        logger.info("buffer ref count:{}", buffer.refCnt());
        buffer.writeBytes(bytes);
        channelHandlerContext.channel().writeAndFlush(buffer);
        print(buffer);
        logger.info("buffer ref count:{}", buffer.refCnt());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        logger.info("ImClient recieve msg");
        ByteBuf data = (ByteBuf) o;

        logger.info("ImServer recieve msg:{}", data.toString(Charset.forName("utf-8")));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        logger.info("exceptionCaught:{}", cause.toString());
        ctx.close() ;
    }

    private void print(ByteBuf buffer){
        logger.info("after ========================");
        logger.info("capacity(): " + buffer.capacity());
        logger.info("maxCapacity(): " + buffer.maxCapacity());
        logger.info("readerIndex(): " + buffer.readerIndex());
        logger.info("readableBytes(): " + buffer.readableBytes());
        logger.info("isReadable(): " + buffer.isReadable());
        logger.info("writerIndex(): " + buffer.writerIndex());
        logger.info("writableBytes(): " + buffer.writableBytes());
        logger.info("isWritable(): " + buffer.isWritable());
        logger.info("maxWritableBytes(): " + buffer.maxWritableBytes());
    }

    private ByteBuf encode(JSONObject jsonObject){
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = jsonObject.getBytes("uft-8");

        // 3. 实际编码过程
        // 魔法值
        byteBuf.writeInt(0);
        // 版本号
        byteBuf.writeByte(1);
        // 指令
        byteBuf.writeByte(1);
        // 长度
        byteBuf.writeInt(bytes.length);
        // 具体的数据
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }
}
