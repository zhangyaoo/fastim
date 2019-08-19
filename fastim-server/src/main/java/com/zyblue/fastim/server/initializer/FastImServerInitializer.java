package com.zyblue.fastim.server.initializer;

import com.zyblue.fastim.server.handler.AuthHandler;
import com.zyblue.fastim.server.handler.FastImServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FastImServerInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        channel.pipeline()
                //.addLast(new ProtobufVarint32FrameDecoder())
                //.addLast(new ProtobufDecoder(CIMRequestProto.CIMReqProtocol.getDefaultInstance()))
                //.addLast(new ProtobufVarint32LengthFieldPrepender())
                //.addLast(new ProtobufEncoder())
                .addLast(new AuthHandler())
                .addLast(new FastImServerHandler());
    }
}
