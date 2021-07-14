package com.zyblue.fastim.client.handler;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author will
 * @date 2021/7/6 14:56
 */
public class MyFastImEncoder extends MessageToByteEncoder<FastImProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, FastImProtocol fastImProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(fastImProtocol.getHeadData());
        byteBuf.writeInt(fastImProtocol.getVersion());
        byteBuf.writeByte(fastImProtocol.getCmd());
        byteBuf.writeByte(fastImProtocol.getMsgType());
        byteBuf.writeInt(fastImProtocol.getLogId());
        byteBuf.writeInt(fastImProtocol.getSequenceId());
        byteBuf.writeByte(fastImProtocol.getDataType());
        byteBuf.writeInt(fastImProtocol.getData().length);
        byteBuf.writeBytes(fastImProtocol.getData());
    }
}
