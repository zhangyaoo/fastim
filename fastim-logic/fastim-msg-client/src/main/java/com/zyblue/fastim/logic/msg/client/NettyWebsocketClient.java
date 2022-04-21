package com.zyblue.fastim.logic.msg.client;

import com.alibaba.nacos.api.exception.NacosException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.timeout.IdleState.WRITER_IDLE;

/**
 * @author will
 */
public class NettyWebsocketClient {

    private static final Logger logger = LogManager.getLogger(NettyWebsocketClient.class);

    private final EventLoopGroup group = new NioEventLoopGroup(1);
    private final Bootstrap bootstrap = new Bootstrap();

    private SslContext sslCtx;

    private NettyWebsocketClient(SslContext sslCtx) {
        this.sslCtx = sslCtx;
        initBootstrap();
    }

    private NettyWebsocketClient() {
        initBootstrap();
    }

    private void initBootstrap() {
        bootstrap.group(group)
                .option(ChannelOption.TCP_NODELAY, true)
//                .option(ChannelOption.SO_BACKLOG, 100000)
                .channel(NioSocketChannel.class);
    }

    public static NettyWebsocketClient buildWebsocketClient() {
        return new NettyWebsocketClient();
    }

    public static NettyWebsocketClient buildSslWebsocketClient() throws SSLException {
        return buildSslWebsocketClient(0);
    }

    public static NettyWebsocketClient buildSslWebsocketClient(int port) throws SSLException {
        if (port == -1 || port == 0) {
            port = 443;
        }
        SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        return new NettyWebsocketClient(sslCtx);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return group.schedule(command, delay, unit);
    }

    public ChannelFuture connect(OnOpenHandler onOpenHandler, OnMessageHandler onMessageHandler, OnCloseHandler onCloseHandler, String host) {
        return connect(onOpenHandler, onMessageHandler, onCloseHandler, host, 0, null);
    }

    public ChannelFuture connect(OnOpenHandler onOpenHandler, OnMessageHandler onMessageHandler, OnCloseHandler onCloseHandler, String host, int port, Map<String, String> headers) {
        return connect(onOpenHandler, onMessageHandler, onCloseHandler, host, port, null, headers);
    }

    public ChannelFuture connect(OnOpenHandler onOpenHandler, OnMessageHandler onMessageHandler,
                                 OnCloseHandler onCloseHandler, String host, int port, String path,
                                 Map<String, String> headers) {
        String url;
        if (sslCtx == null) {
            if (port < 1) {
                port = 80;
            }
            url = "ws://" + host + ":" + port;
        } else {
            if (port < 1) {
                port = 443;
            }
            url = "wss://" + host + ":" + port;
        }
        if (StringUtils.hasText(path)) {
            url += path;
        }

        URI uri = URI.create(url);

        int finalPort = port;

        synchronized (this) {
            bootstrap
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, finalPort));
                            }
                            pipeline.addLast(
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(209715200),
                                    WebSocketClientCompressionHandler.INSTANCE,
                                    new IdleStateHandler(0, 3, 0),
                                    new WebSocketClientHandler(onOpenHandler, onMessageHandler, onCloseHandler, uri, headers)
                            );
                        }
                    });
            try {
                ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
                channelFuture.addListener(future -> {
                    if (future.isSuccess()){
                        logger.info("connect success");
                    }
                });
                return channelFuture;
            } catch (Exception e) {
                logger.warn("连接server异常 host:{} port:{}", host, port, e);
            }
            return null;
        }
    }

    public void disconnectAll() {
        try {
            group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface OnMessageHandler {
        void onMessage(Channel channel, String text);
    }

    @FunctionalInterface
    public interface OnCloseHandler {
        void onClose(Channel channel) throws NacosException;
    }

    @FunctionalInterface
    public interface OnOpenHandler {
        void onOpen(Channel channel);
    }

    private static class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

        private WebSocketClientHandshaker handshaker;
        private final OnOpenHandler onOpenHandler;
        private final OnMessageHandler onMessageHandler;
        private final OnCloseHandler onCloseHandler;
        private final URI uri;
        private final Map<String, String> attachingHeaders;

        public WebSocketClientHandler(OnOpenHandler onOpenHandler, OnMessageHandler onMessageHandler, OnCloseHandler onCloseHandler, URI uri, Map<String, String> headers) {
            this.onOpenHandler = onOpenHandler;
            this.onMessageHandler = onMessageHandler;
            this.onCloseHandler = onCloseHandler;
            this.uri = uri;
            this.attachingHeaders = headers;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            DefaultHttpHeaders headers = new DefaultHttpHeaders();
            headers.add(HttpHeaderNames.HOST, uri.getHost());
            if (attachingHeaders != null) {
                attachingHeaders.forEach(headers::add);
            }
            handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13, null, true, headers, 209715200);
            handshaker.handshake(ctx.channel());
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
            if (!handshaker.isHandshakeComplete()) {
                handshaker.finishHandshake(channelHandlerContext.channel(), (FullHttpResponse) msg);
                onOpenHandler.onOpen(channelHandlerContext.channel());
                return;
            }

            if (msg instanceof FullHttpResponse) {
                FullHttpResponse response = (FullHttpResponse) msg;
                throw new IllegalStateException(
                        "Unexpected FullHttpResponse (getStatus=" + response.status() +
                                ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
            }

            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                onMessageHandler.onMessage(channelHandlerContext.channel(), textFrame.text());
            } else if (frame instanceof BinaryWebSocketFrame) {
            } else if (frame instanceof PongWebSocketFrame) {
            } else if (frame instanceof CloseWebSocketFrame) {
                logger.debug("接收到server传来的close帧 remoteAddress:{}", channelHandlerContext.channel().remoteAddress());
                channelHandlerContext.channel().close();
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("连接关闭 remoteAddress:{}", ctx.channel().remoteAddress());
            onCloseHandler.onClose(ctx.channel());
            super.channelInactive(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
            logger.warn("连接抛异常", t);
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
    }
}
