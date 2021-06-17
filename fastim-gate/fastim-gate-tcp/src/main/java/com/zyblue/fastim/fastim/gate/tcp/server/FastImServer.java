package com.zyblue.fastim.fastim.gate.tcp.server;

import com.zyblue.fastim.fastim.gate.tcp.handler.DynamicDecodeHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * IMServer生命周期跟随spring
 */
@Component
public class FastImServer {

    private final static Logger logger = LoggerFactory.getLogger(FastImServer.class);

    @Value("${fastim.server.port}")
    private int nettyPort;

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    private Channel serverChannel;

    @PostConstruct
    public void start() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        logger.info("cpuCount:{}", cpuCount);
        bossGroup = new NioEventLoopGroup(cpuCount);
        workerGroup = new NioEventLoopGroup(cpuCount << 1 + 1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // TODO 长连接TTL
                // 给服务器端连接请求队列中队列的大小,达到上限则不进行新的tcp连接
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 客户端连接是否长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 客户端连接开启Nagle算法，true表示关闭，false表示开启   高实时性就关闭，否则开启
                /*
                 * 其他TCP参数详解：
                 * SO_SNDBUF 发送缓冲区大小
                 * 缓冲区太小，会降低TCP吞吐量，无法高效利用网络带宽，导致通信延迟升高；缓冲区太大，会导致TCP连接内存占用高以及受限于带宽时延积的瓶颈，从而造成内存浪费。Linux系统可以自动进行动态调节，所以不要轻易设置TCP缓冲区大小
                 */
                .childOption(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    // handler供boss使用，childHandler供worker使用
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) {
                        logger.info("服务端启动中");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel channel){
                        channel.pipeline().addLast(new DynamicDecodeHandler());
                    }
                });
        bind(serverBootstrap, nettyPort);
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
                logger.info("重新绑定ing");
                bind(serverBootstrap, nettyPort + 1);
            }
        });
        serverChannel =  future2.channel();
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
}
