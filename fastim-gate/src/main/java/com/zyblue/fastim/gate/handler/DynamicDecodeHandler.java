package com.zyblue.fastim.gate.handler;

import com.zyblue.fastim.gate.handler.gate.GateAuthHandler;
import com.zyblue.fastim.gate.util.HeaderParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.internal.AppendableCharSequence;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/4/29 16:21
 * 动态选择器
 */
public class DynamicDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if(headerParser.isHttp(byteBuf)){
            addTCPProtocolHandlers(channelHandlerContext);
        }else {
            addHTTPHandlers(channelHandlerContext);
        }
        // 删除channel分发
        channelHandlerContext.pipeline().remove(this);
        // //转到下一个handle ctx.fireChannelActive();
    }

    /**
     * 动态添加TCP私有协议处理器
     */
    private void addTCPProtocolHandlers(ChannelHandlerContext channelHandlerContext) {
        /*
         * 服务端发现 180 秒未从客户端读取到消息，主动断开连接
         */
        channelHandlerContext.pipeline().addLast(new ReadTimeoutHandler(180, TimeUnit.SECONDS));
        channelHandlerContext.pipeline().addLast(new ProtobufVarint32FrameDecoder());
        channelHandlerContext.pipeline().addLast(new ProtobufDecoder());
        channelHandlerContext.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
        channelHandlerContext.pipeline().addLast(new ProtobufEncoder());

        channelHandlerContext.pipeline().addLast(new AuthHandler());
        channelHandlerContext.pipeline().addLast(new FastImServerHandler());
    }

    /**
     * 动态添加HTTP处理器
     */
    private void addHTTPHandlers(ChannelHandlerContext channelHandlerContext) {
        // HttpServerCodec只能获取uri中参数，所以需要加上HttpObjectAggregator
        channelHandlerContext.pipeline().addLast(new HttpServerCodec());
        channelHandlerContext.pipeline().addLast(new HttpObjectAggregator(8192));
        // ChunkedWriteHandler来解决大文件或者码流传输过程中可能发生的内存溢出问题
        channelHandlerContext.pipeline().addLast(new ChunkedWriteHandler());
        channelHandlerContext.pipeline().addLast(new GateAuthHandler());
    }

    /**
     * 初始一个字节数组大小为128的初始容量
     * 最大行读取字节为4096，来判断是否是http请求
     * 参考 HttpObjectDecoder类
     */
    private final HeaderParser headerParser = new HeaderParser(new AppendableCharSequence(128), 4096);

    private final byte[] POST_HEADER_BYTES = new byte[]{50, (byte) 4f, 53, 54};

    private final byte[] GET_HEADER_BYTES = new byte[]{0X47, 0X45, 0X54, 0X20};

    private boolean isHttp(ByteBuf buffer){
        byte[] src = new byte[buffer.readableBytes()];
        buffer.copy(0, buffer.readableBytes()).readBytes(src);
        if (this.startWithsArrays(src, GET_HEADER_BYTES) || this.startWithsArrays(src, POST_HEADER_BYTES)) {
            return true;
        }
        return false;
    }

    /**
     * 查看数组的开头是否为目标数组
     */
    public boolean startWithsArrays(byte[] src ,byte[] featureBytes) {
        int r = 0;
        int i =0;
        for (; i < featureBytes.length; i++) {
            r += src[i] ==featureBytes[i] ? 1 : 0;
        }
        return r!=0 && i==r;
    }
}
