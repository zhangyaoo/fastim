package com.zyblue.fastim.gate.nettyserver;

import com.zyblue.fastim.gate.initializer.FastImServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Component：
 * 1、默认单例对象
 * 2、随spring容器销毁 IMserver就shutdown
 */
@Component
public class FastImServer {

    private final static Logger logger = LoggerFactory.getLogger(FastImServer.class);

    @Value("${fastim.server.port}")
    private int nettyPort;

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;

    @PostConstruct
    public void start() throws InterruptedException{
        int cpuCount = Runtime.getRuntime().availableProcessors();
        logger.info("cpuCount:{}", cpuCount);
        bossGroup = new NioEventLoopGroup(cpuCount);
        workerGroup = new NioEventLoopGroup(2*cpuCount + 1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 给服务器端连接请求队列中队列的大小,达到上限则不进行新的tcp连接
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 客户端连接是否长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 客户端连接开启Nagle算法，true表示关闭，false表示开启   高实时性就关闭，否则开启
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    // handler供boss使用，childHandler供worker使用
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) {
                        System.out.println("服务端启动中");
                    }
                })
                .childHandler(new FastImServerInitializer());
        bind(serverBootstrap, nettyPort);
    }

    private void bind(ServerBootstrap serverBootstrap, Integer nettyPort){
        serverBootstrap.bind(nettyPort).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    logger.info("端口绑定成功 ImServer start success!");
                } else {
                    logger.info("端口绑定失败! port:{}", nettyPort);
                    logger.info("重新绑定ing");
                    bind(serverBootstrap, nettyPort + 1);
                }
            }
        });
    }

    @PreDestroy
    private void destroy(){
        if(bossGroup != null && !bossGroup.isShutdown()){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null && !workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
    }
}
