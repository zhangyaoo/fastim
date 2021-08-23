package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.gate.GateMsgResponse;
import com.zyblue.fastim.common.pojo.gate.GateMsgResponseCode;
import com.zyblue.fastim.common.pojo.gate.InterfaceMetadata;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import com.zyblue.fastim.fastim.gate.tcp.constant.CmdType;
import com.zyblue.fastim.fastim.gate.tcp.util.AttributeUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @author will
 * @date 2021/4/30 12:46
 * 协议转换
 */
@ChannelHandler.Sharable
public class GateProtocolConversionHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final static Logger log = LoggerFactory.getLogger(GateProtocolConversionHandler.class);

    private final ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) {
        CmdType cmdType = CmdType.getCmdTypeByVal(protocol.getCmd());
        if(cmdType == null){
            log.error("CmdType is illegal");
            writeFailedMessage(channelHandlerContext, protocol, "CmdType is illegal");
            return;
        }

        String address = AttributeUtil.getAddress(channelHandlerContext.channel());
        InterfaceMetadata interfaceMetadata = transferParam(protocol, cmdType);
        if(interfaceMetadata == null){
            log.error("注册中心未找到服务");
            writeFailedMessage(channelHandlerContext, protocol, "服务器内部错误");
            return;
        }

        reference.setInterface(interfaceMetadata.getInterfaceName());
        reference.setVersion(interfaceMetadata.getInterfaceVersion());
        reference.setGeneric("true");
        reference.setCheck(false);
        reference.setUrl(address);
        GenericService genericService = reference.get();
        CompletableFuture<Object> future = genericService.$invokeAsync(interfaceMetadata.getMethodName(),
                interfaceMetadata.getParameterTypes(), interfaceMetadata.getParams());

        /*
         * 保证发送的线程和返回数据的线程是同一个，目的是为了减少线程切换
         */
        future.whenCompleteAsync((result, throwable) -> {
            /*
             * DUBBO和HTTP是request-response请求响应模型，而IM通讯场景会有request而没有response
             */
            if(result != null){
                channelHandlerContext.writeAndFlush(result);
            }
        }, channelHandlerContext.channel().eventLoop());
    }

    /**
     * 根据规则转换成参数
     */
    private InterfaceMetadata transferParam(FastImMsg protocol, CmdType cmdType){
        // 根据服务名和方法获取入参类型
        String mapService = cmdType.getMapService();
        String mapMethod = cmdType.getMapMethod();


        return null;
    }


    private void writeFailedMessage(ChannelHandlerContext channelHandlerContext, FastImMsg protocol, String failMsg){
        GateMsgResponse gateMsgResponse = new GateMsgResponse();
        gateMsgResponse.setCode(GateMsgResponseCode.PARAM_ERROR);
        gateMsgResponse.setMessage(failMsg);

        protocol.setMsgType(MsgType.ACK.getVal());
        protocol.setData(ProtoStuffUtils.serialize(gateMsgResponse));
        channelHandlerContext.writeAndFlush(protocol);
    }
}
