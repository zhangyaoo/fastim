package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.fastim.gate.tcp.util.AttributeUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author will
 * @date 2021/4/30 12:44
 * 动态路由
 */
@ChannelHandler.Sharable
public class GateDynamicRouteHandler extends SimpleChannelInboundHandler<FastImMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) throws Exception {
        channelHandlerContext.fireChannelRead(protocol);
        byte cmd = protocol.getCmd();

        String serverIp = getServerInfoByServiceNameAndMethod(protocol);

        AttributeUtil.setAddress(channelHandlerContext.channel(), serverIp);
    }


    /**
     * 根据服务名称和方法获取服务地址
     */
    public String getServerInfoByServiceNameAndMethod(FastImMsg protocol) {

        return null;
    }
}
