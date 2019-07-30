package com.zyblue.fastim.client.initializer;

import com.zyblue.fastim.client.handler.FastImClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class FastImClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //.addLast(new ProtobufVarint32FrameDecoder())
                //.addLast(new ProtobufDecoder(CIMResponseProto.CIMResProtocol.getDefaultInstance()))
                //.addLast(new ProtobufVarint32LengthFieldPrepender())
                //.addLast(new ProtobufEncoder())
                .addLast(new FastImClientHandler());
    }
}
