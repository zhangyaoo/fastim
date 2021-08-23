package com.zyblue.fastim.fastim.gate.http.plugin;

import com.zyblue.fastim.fastim.gate.http.plugin.chain.HttpHandlerContext;
import com.zyblue.fastim.common.pojo.gate.InterfaceMetadata;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * @author will
 * @date 2021/4/30 12:46
 * 协议转换
 */
@Component
public class GateProtocolConversionHandler implements GatePluginHandler{

    private final static Logger logger = LoggerFactory.getLogger(GateProtocolConversionHandler.class);

    private final ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

    @Override
    public void execute(HttpHandlerContext context, DefaultHttpRequest request) {
        logger.info("GateProtocolConversionHandler execute");
        InterfaceMetadata interfaceMetadata = transferParam(request);

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
        }, context.getChannelHandlerContext().executor());
    }

    /**
     * 根据规则转换成参数
     */
    private InterfaceMetadata transferParam(DefaultHttpRequest request){
        return new InterfaceMetadata();
    }

    @Override
    public void response(HttpHandlerContext context, DefaultHttpRequest request, DefaultFullHttpResponse response) {
        logger.info("GateProtocolConversionHandler response");
        context.fireResult(request, response);
    }
}
