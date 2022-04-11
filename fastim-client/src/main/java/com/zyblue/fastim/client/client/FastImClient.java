package com.zyblue.fastim.client.client;

import com.zyblue.fastim.client.handler.FastImClientHandler;
import com.zyblue.fastim.client.handler.MyFastImDecoder;
import com.zyblue.fastim.client.handler.MyFastImEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 滚滚长江东流水ing
 * @author will
 */
public class FastImClient {

    private final static Logger logger = LoggerFactory.getLogger(FastImClient.class);

    private NioEventLoopGroup workerGroup;
    private Bootstrap bootstrap;

    public FastImClient() {
        bootstrap = initBootstrap();
    }

    private Bootstrap initBootstrap() {
        // 1个NioEventLoop专门接收请求，1个NioEventLoop处理ACK
        workerGroup = new NioEventLoopGroup(1);

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                /*
                                 * 客户端发现 180 秒未从服务端读取到消息，主动断开连接
                                 */
                                .addLast(new ReadTimeoutHandler(180, TimeUnit.SECONDS))
                                /*
                                 * 服务端每 60 秒向服务端发起一次心跳消息，保证客户端端可以读取到消息。有3次机会保证不断开
                                 */
                                .addLast(new IdleStateHandler(60, 0, 0))
                                .addLast(new MyFastImDecoder())
                                .addLast(new MyFastImEncoder())
                                .addLast(new FastImClientHandler());
                    }
                });
        return bootstrap;
    }

    /**
     * 客户端重连
     */
    public NioSocketChannel connect(String host, Integer port, OnSuccessFuture onSuccessFuture) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync().addListener(onSuccessFuture::onFuture);
            return (NioSocketChannel) channelFuture.channel();
        } catch (InterruptedException e) {
            logger.error("error", e);
            return null;
        }
    }

    @FunctionalInterface
    public interface OnSuccessFuture {
        /**
         * onFuture
         * @param future future
         */
        void onFuture(Future<?> future);
    }

    public void destroy() {
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }
}
