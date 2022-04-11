package com.zyblue.fastim.fastim.gate.tcp.server;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.zyblue.fastim.fastim.gate.tcp.handler.codec.MyFastImDecoder;
import com.zyblue.fastim.fastim.gate.tcp.handler.codec.MyFastImEncoder;
import com.zyblue.fastim.fastim.gate.tcp.handler.gate.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * server
 * @author zhangy75
 */
@Component
public class FastImServer {

    private final static Logger logger = LoggerFactory.getLogger(FastImServer.class);

    @Value("${fastim.server.port}")
    private int nettyPort;

    private NioEventLoopGroup bossGroup;

    @Resource(name = "nioEventLoopGroup")
    private NioEventLoopGroup workerGroup;

    @NacosInjected
    private NamingService namingService;

    @Value("${instance.server-logic.name}")
    private String instanceName;

    private NioServerSocketChannel serverChannel;

    @PostConstruct
    public void start() {
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        //GateAuthHandler gateAuthHandler = new GateAuthHandler();
        GateMetricsHandler gateMetricsHandler = new GateMetricsHandler();
        //GateServiceSelfProtectHandler gateServiceSelfProtectHandler = new GateServiceSelfProtectHandler();
        GateDynamicRouteHandler gateDynamicRouteHandler = new GateDynamicRouteHandler(namingService, instanceName);
        GateProtocolConversionHandler gateProtocolConversionHandler = new GateProtocolConversionHandler(namingService, instanceName);
        //GateServiceTimeoutHandler gateServiceTimeoutHandler = new GateServiceTimeoutHandler();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) {
                        logger.info("服务端启动中");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel channel){
                        channel.pipeline()
                                .addLast("fastImEncoder" ,new MyFastImEncoder())
                                .addLast("fastImDecoder", new MyFastImDecoder())
                                .addLast(gateMetricsHandler)
                                .addLast(loggingHandler)
                                //.addLast(gateAuthHandler)
                                //.addLast("limitHandler", new GateLimitHandler())
                                //.addLast(gateServiceSelfProtectHandler)
                                .addLast(gateDynamicRouteHandler)
                                //.addLast(gateServiceTimeoutHandler)
                                .addLast(gateProtocolConversionHandler);
                    }
                });
        bind(serverBootstrap, nettyPort);
    }

    @PreDestroy
    private void destroy(){
        if (serverChannel != null) {
            serverChannel.close();
        }
        if(bossGroup != null && !bossGroup.isShutdown()){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null && !workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 绑定端口
     */
    private void bind(ServerBootstrap serverBootstrap, Integer nettyPort){
        ChannelFuture future2 = serverBootstrap.bind(nettyPort).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("端口绑定成功 ImServer start success!");
            } else {
                logger.info("端口绑定失败! port:{}", nettyPort);
            }
        });
        serverChannel = (NioServerSocketChannel) future2.channel();
    }
}
