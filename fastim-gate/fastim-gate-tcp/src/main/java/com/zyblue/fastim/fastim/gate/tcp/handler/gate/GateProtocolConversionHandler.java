package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.ServerInfo;
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
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author will
 * @date 2021/4/30 12:46
 * 协议转换
 */
@ChannelHandler.Sharable
public class GateProtocolConversionHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final static Logger log = LoggerFactory.getLogger(GateProtocolConversionHandler.class);

    private final ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

    private final NamingService namingService;

    private final String instanceName;

    public GateProtocolConversionHandler(NamingService namingService, String instanceName) {
        this.namingService = namingService;
        this.instanceName = instanceName;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) {
        CmdType cmdType = CmdType.getCmdTypeByVal(protocol.getCmd());
        if(cmdType == null){
            log.error("CmdType is illegal");
            writeFailedMessage(channelHandlerContext, protocol, "CmdType is illegal");
            return;
        }

        List<ServerInfo> address = AttributeUtil.getAddress(channelHandlerContext.channel());
        int index = ThreadLocalRandom.current().nextInt(0, address.size());
        index = Math.min(index, address.size() - 1);

        InterfaceMetadata interfaceMetadata;
        try {
            interfaceMetadata = transferParam(protocol, cmdType);
        } catch (NacosException e) {
            log.error("nacos error");
            writeFailedMessage(channelHandlerContext, protocol, "服务器内部错误");
            return;
        }

        reference.setInterface(interfaceMetadata.getInterfaceName());
        //reference.setVersion(interfaceMetadata.getInterfaceVersion());
        reference.setGeneric("true");
        reference.setCheck(false);
        reference.setUrl("dubbo://" + address.get(index).getIp() + ":" + address.get(index).getServerPort());
        GenericService genericService = reference.get();
        CompletableFuture<Object> future = genericService.$invokeAsync(interfaceMetadata.getMethodName(),
                interfaceMetadata.getParameterTypes(), interfaceMetadata.getParams());

        // TODO 这里代码有问题，因为IM业务特殊性问题，网关层到服务层，只需要单向传输发请求，网关层不需要关心调用的结果。
        //  而客户端想要的ack或者notify请求是由router层发送数据到网关层，网关层只转发数据，不做额外的逻辑处理
        /*
         * 保证发送的线程和返回数据的线程是同一个
         */
        future.whenCompleteAsync((result, throwable) -> {
            /*
             * DUBBO和HTTP是request-response请求响应模型，而IM通讯场景会有request而没有response
             */
            if(result != null){
                protocol.setData(ProtoStuffUtils.serialize(result));
                channelHandlerContext.writeAndFlush(protocol);
            }
        }, channelHandlerContext.channel().eventLoop());
    }

    /**
     * 根据规则转换成参数
     */
    private InterfaceMetadata transferParam(FastImMsg protocol, CmdType cmdType) throws NacosException {
        // 根据服务名和方法获取入参类型
        String mapService = cmdType.getMapService();
        String mapMethod = cmdType.getMapMethod();

        List<Instance> allInstances = namingService.getAllInstances(instanceName);
        if(CollectionUtils.isEmpty(allInstances)){
            throw new NacosException();
        }

        Instance instance = allInstances.get(0);
        Map<String, String> metadata = instance.getMetadata();

        InterfaceMetadata interfaceMetadata = new InterfaceMetadata();

        return interfaceMetadata;
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
