package com.zyblue.fastim.fastim.gate.http.plugin;

import com.zyblue.fastim.fastim.gate.http.plugin.chain.HttpHandlerContext;
import com.zyblue.fastim.fastim.gate.http.pojo.InterfaceMetadata;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.*;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "nioThreadPool")
    private NioEventLoopGroup workerGroup;

    private final ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

    @Override
    public void execute(HttpHandlerContext context, DefaultHttpRequest request) {
        InterfaceMetadata interfaceMetadata = transferParam(request);

        reference.setInterface(interfaceMetadata.getInterfaceName());
        reference.setVersion(interfaceMetadata.getInterfaceVersion());
        reference.setGeneric("true");
        reference.setCheck(false);
        GenericService genericService = reference.get();
        CompletableFuture<Object> future = genericService.$invokeAsync(interfaceMetadata.getMethodName(),
                interfaceMetadata.getParameterTypes(), interfaceMetadata.getParams());
        /*
         * TODO 请求和响应如何保证是同一条线程？
         */
        future.whenCompleteAsync((result, throwable) -> {
            HttpResponseStatus status = HttpResponseStatus.OK;
            if(throwable != null){
                logger.error("error:", throwable);
                status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
            }
            ByteBuf buf = Unpooled.wrappedBuffer("result".getBytes(StandardCharsets.UTF_8));
            DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
            response(context, request, defaultHttpResponse);
        }, workerGroup);
    }

    /**
     * 根据规则转换成参数
     */
    private InterfaceMetadata transferParam(DefaultHttpRequest request){
        return new InterfaceMetadata();
    }

    @Override
    public void response(HttpHandlerContext context, DefaultHttpRequest request, DefaultFullHttpResponse response) {
        context.fireResult(request, response);
    }
}
