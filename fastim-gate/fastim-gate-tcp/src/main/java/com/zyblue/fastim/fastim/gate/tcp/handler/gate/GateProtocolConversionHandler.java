package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 12:46
 * 协议转换
 */
@ChannelHandler.Sharable
public class GateProtocolConversionHandler extends SimpleChannelInboundHandler<FastImProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImProtocol protocol) throws Exception {
        /*InterfaceMetadata interfaceMetadata = transferParam(request);

        reference.setInterface(interfaceMetadata.getInterfaceName());
        reference.setVersion(interfaceMetadata.getInterfaceVersion());
        reference.setGeneric("true");
        reference.setCheck(false);
        GenericService genericService = reference.get();
        CompletableFuture<Object> future = genericService.$invokeAsync(interfaceMetadata.getMethodName(),
                interfaceMetadata.getParameterTypes(), interfaceMetadata.getParams());

        future.whenCompleteAsync((result, throwable) -> {
            HttpResponseStatus status = HttpResponseStatus.OK;
            if(throwable != null){
                logger.error("error:", throwable);
                status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
            }
            ByteBuf buf = Unpooled.wrappedBuffer("result".getBytes(StandardCharsets.UTF_8));
            DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
            response(context, request, defaultHttpResponse);
        }, context.getChannelHandlerContext().executor());*/

        channelHandlerContext.fireChannelRead(protocol);

    }
}
