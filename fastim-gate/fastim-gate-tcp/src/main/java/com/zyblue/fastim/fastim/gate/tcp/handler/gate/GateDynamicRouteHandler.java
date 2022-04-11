package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.enumeration.MsgType;
import com.zyblue.fastim.common.pojo.ServerInfo;
import com.zyblue.fastim.common.pojo.gate.GateMsgResponse;
import com.zyblue.fastim.common.pojo.gate.GateMsgResponseCode;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import com.zyblue.fastim.fastim.gate.tcp.util.AttributeUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author will
 * @date 2021/4/30 12:44
 * 动态路由
 */
@ChannelHandler.Sharable
public class GateDynamicRouteHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final static Logger logger = LoggerFactory.getLogger(GateDynamicRouteHandler.class);

    public GateDynamicRouteHandler(NamingService namingService, String instanceName) {
        this.namingService = namingService;
        this.instanceName = instanceName;
    }

    private final NamingService namingService;

    private final String instanceName;

    private final static String META_KEY = "cmd";

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) throws Exception {
        List<ServerInfo> serverIps;
        try {
            serverIps = getServerInfoByServiceNameAndMethod(protocol);
        }catch (NacosException e){
            writeFailedMessage(channelHandlerContext, protocol, "服务器内部错误");
            return;
        }
        AttributeUtil.setAddress(channelHandlerContext.channel(), serverIps);
        channelHandlerContext.fireChannelRead(protocol);
    }

    /**
     * 返回符合条件的所有服务地址
     */
    public List<ServerInfo> getServerInfoByServiceNameAndMethod(FastImMsg protocol) throws NacosException {
        List<Instance> allInstances  = namingService.getAllInstances(instanceName);
        return allInstances.stream().filter(y -> {
            boolean contain = y.getMetadata().containsKey(META_KEY);
            if(contain){
                return Integer.parseInt(y.getMetadata().get(META_KEY)) == protocol.getCmd();
            }
            return false;
        }).map(x ->{
            ServerInfo serverInfo = new ServerInfo();
            serverInfo.setServerPort(x.getPort());
            serverInfo.setIp(x.getIp());
            return serverInfo;
        }).collect(Collectors.toList());
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
