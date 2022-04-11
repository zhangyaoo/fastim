package com.zyblue.fastim.client.handler;

import com.zyblue.fastim.client.constant.CmdType;
import com.zyblue.fastim.client.service.impl.GroupChatServiceImpl;
import com.zyblue.fastim.client.service.impl.HeartbeatServiceImpl;
import com.zyblue.fastim.client.service.impl.PushServiceImpl;
import com.zyblue.fastim.client.service.impl.SingleChatServiceImpl;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.util.JacksonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import static io.netty.handler.timeout.IdleState.WRITER_IDLE;

/**
 * 客户端处理
 * @author will
 */
public class FastImWebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private final static Logger logger = LoggerFactory.getLogger(FastImWebSocketClientHandler.class);

    private WebSocketClientHandshaker handshake;

    private final URI uri;

    public FastImWebSocketClientHandler(URI uri) {
        this.uri = uri;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (WRITER_IDLE.equals(idleStateEvent.state())) {
                ctx.channel().writeAndFlush(new PingWebSocketFrame());
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        handshake = WebSocketClientHandshakerFactory.newHandshaker(
                uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        handshake.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

        Channel ch = channelHandlerContext.channel();
        if (!handshake.isHandshakeComplete()) {
            try {
                handshake.finishHandshake(ch, (FullHttpResponse) msg);
                System.out.println("WebSocket Client connected!");
            } catch (WebSocketHandshakeException e) {
                System.out.println("WebSocket Client failed to connect");
            }
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.getStatus()
                    + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            FastImMsg fastImMsg = JacksonUtils.json2pojo(textFrame.text(), FastImMsg.class);

            CmdType cmdType = CmdType.getCmdTypeByVal(fastImMsg.getCmd());

            if(cmdType == null){
                logger.error("param error: cmd");
                throw new IllegalStateException("param error: cmd");
            }

            switch (cmdType){
                case SINGLE_CHAT:
                    SingleChatServiceImpl.getInstance().received(fastImMsg);break;
                case GROUP_CHAT:
                    GroupChatServiceImpl.getInstance().received(fastImMsg);break;
                case HEARTBEAT:
                    HeartbeatServiceImpl.getInstance().received(fastImMsg);break;
                case PUSH:
                    PushServiceImpl.getInstance().received(fastImMsg);break;
                default:break;
            }
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }
    }
}
