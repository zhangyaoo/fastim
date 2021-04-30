package com.zyblue.fastim.client.nettyclient;

import com.zyblue.fastim.client.initializer.FastImClientInitializer;
import com.zyblue.fastim.client.service.BizService;
import com.zyblue.fastim.common.codec.Invocation;
import com.zyblue.fastim.common.codec.InvocationType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * 滚滚长江东流水ing
 */
@Component
public class FastImClient {

    private final static Logger logger = LoggerFactory.getLogger(FastImClient.class);

    private final Integer maxRetry = 5;

    @Autowired
    private BizService bizService;

    @Value("${gate.url}")
    private String gateUrl;

    @Value("${gate.port}")
    private Integer gatePort;

    private NioEventLoopGroup workerGroup;

    /**
     * 客户端channel
     */
    private Channel channel;

    @PostConstruct
    public void start() {
        logger.info("fast im client starting..");

        workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                // .attr(AttributeKey.newInstance("token"), loginResponse.getToken())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new FastImClientInitializer());
        connect(bootstrap, maxRetry);
    }

    /**
     * 客户端重连
     */
    private void connect(Bootstrap bootstrap, Integer retryTimes){
        ChannelFuture channelFuture = bootstrap.connect(gateUrl, gatePort).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("连接成功!");
            } else if (retryTimes == 0) {
                logger.info("重试次数已用完，放弃连接！");
            } else {
                int order = (maxRetry - retryTimes) + 1;
                logger.info("客户端第{}次重新ing", order);
                int delay = 1 << order;
                // 定时到点执行
                bootstrap.config().group().schedule(() -> connect(bootstrap, retryTimes - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
        channel = channelFuture.channel();
    }

    /**
     * 发送消息
     * @param msg 消息体
     */
    public <T> ChannelFuture send(T msg) {
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return null;
        }
        if (!channel.isActive()) {
            logger.error("[send][连接({})未激活]", channel.id());
            return null;
        }
        // 发送消息
        return channel.writeAndFlush(new Invocation<T>(InvocationType.CUSTOM_TCP.getVal(), msg));
    }

    @PreDestroy
    public void destroy(){
        if(workerGroup != null && !workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
    }
}
