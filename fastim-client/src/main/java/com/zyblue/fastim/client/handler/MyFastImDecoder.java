package com.zyblue.fastim.client.handler;


import com.zyblue.fastim.common.codec.FastImProtocol;
import com.zyblue.fastim.common.constant.CommonConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author will
 * @date 2021/7/6 14:56
 */
public class MyFastImDecoder extends ByteToMessageDecoder {

    /**
     * 按照协议头来解析
     */
    public final int BASE_LENGTH = 1 + 4 + 1 + 1 + 4 + 4 + 1 + 4;

    /**
     * 数据包最大大小 2M
     */
    public final int MAX_DATA_LENGTH = 2048;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 可读字节是否大于8
        if(byteBuf.readableBytes() < BASE_LENGTH){
            // 等待完整数据包
            return;
        }

        // 跳过不需要读取的字节码
        if(byteBuf.readableBytes() > MAX_DATA_LENGTH){
            byteBuf.skipBytes(byteBuf.readableBytes());
        }

        int beginReader;
        while (true){
            // 获取包头开始的index
            beginReader = byteBuf.readerIndex();
            // 标记包头开始的index
            byteBuf.markReaderIndex();

            if(byteBuf.readByte() == CommonConstant.Protocol.HEAD_DATA){
                break;
            }

            // 到这里表明没有读取到包头标识，继续往后读取一个字节
            byteBuf.resetReaderIndex();
            byteBuf.readByte();

            // 等待完整数据包
            if(byteBuf.readableBytes() < BASE_LENGTH){
                return;
            }
        }

        // 到这表明读取到了头标识magic，需要读取头其他数据
        int version = byteBuf.readInt();
        byte cmd = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        int logId = byteBuf.readInt();
        int sequenceId = byteBuf.readInt();
        byte dataType = byteBuf.readByte();
        int bodyLength = byteBuf.readInt();

        if(byteBuf.readableBytes() < bodyLength){
            // 表明 body还未读取完，还原读指针
            byteBuf.readerIndex(beginReader);
            return;
        }

        byte[] bodyData = new byte[bodyLength];
        byteBuf.readBytes(bodyData);

        FastImProtocol protocol = new FastImProtocol();
        protocol.setVersion(version);
        protocol.setCmd(cmd);
        protocol.setMsgType(msgType);
        protocol.setLogId(logId);
        protocol.setSequenceId(sequenceId);
        protocol.setDataType(dataType);
        protocol.setData(bodyData);
        list.add(protocol);
    }

    /**
     * ByteBuf转String
     */
    public String convertByteBufToString(ByteBuf buf) throws UnsupportedEncodingException {
        String str;
        // 处理堆缓冲区
        if(buf.hasArray()) {
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        }
        // 处理直接缓冲区以及复合缓冲区
        else {
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }
}
