package com.zyblue.fastim.fastim.gate.tcp.handler.gate;

import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 认证handler
 * 认证通过后可以移除此handler
 */
@ChannelHandler.Sharable
public class GateAuthHandler extends SimpleChannelInboundHandler<FastImMsg> {

    private final static Logger log = LoggerFactory.getLogger(GateAuthHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FastImMsg protocol) throws Exception {
        try {

        } catch (Exception e) {
            log.error("exception happen for: " + e.getMessage(), e);
            channelHandlerContext.close();
        } finally {
            // 认证不通过，也移除，因为连接已经关闭
            channelHandlerContext.pipeline().remove(this);
        }
    }
}
