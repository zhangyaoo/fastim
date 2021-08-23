package com.zyblue.fastim.fastim.gate.tcp.handler.codec;

import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author will
 * @date 2021/7/6 14:56
 */
public class MyFastImEncoder extends MessageToByteEncoder<FastImMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, FastImMsg fastImMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(fastImMsg.getHeadData());
        byteBuf.writeInt(fastImMsg.getVersion());
        byteBuf.writeByte(fastImMsg.getCmd());
        byteBuf.writeByte(fastImMsg.getMsgType());
        byteBuf.writeInt(fastImMsg.getLogId());
        byteBuf.writeInt(fastImMsg.getSequenceId());
        byteBuf.writeInt(fastImMsg.getData().length);
        byteBuf.writeBytes(fastImMsg.getData());
    }
}
