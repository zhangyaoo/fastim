package com.zyblue.fastim.client.nettyclient;

import com.zyblue.fastim.client.initializer.FastImClientInitializer;
import com.zyblue.fastim.client.service.GateService;
import com.zyblue.fastim.common.ServerInfo;
import com.zyblue.fastim.common.request.LoginRequest;
import com.zyblue.fastim.common.response.LoginResponse;
import com.zyblue.fastim.common.vo.BaseResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Integer maxRetry = 5;

    @Autowired
    private GateService gateService;

    private NioEventLoopGroup workerGroup;

    @PostConstruct
    public void start() {
        logger.info("fast im client starting..");

        LoginResponse loginResponse = login();
        logger.info("loginResponse:{}", loginResponse);
        if(null == loginResponse){
            throw new NullPointerException("loginResponse is null");
        }
        workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .attr(AttributeKey.newInstance("token"), loginResponse.getToken())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new FastImClientInitializer());
        connect(bootstrap, loginResponse.getServerInfo(), maxRetry);
    }

    private void connect(Bootstrap bootstrap, ServerInfo serverInfo, Integer retryTimes){
        bootstrap.connect(serverInfo.getIp(), serverInfo.getServerPort()).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("连接成功!");
            } else if (retryTimes == 0) {
                logger.info("重试次数已用完，放弃连接！");
            } else {
                int order = (maxRetry - retryTimes) + 1;
                logger.info("客户端第{}次重新ing", order);
                int delay = 1 << order;
                // 定时到点执行
                bootstrap.config().group().schedule(() -> connect(bootstrap, serverInfo, retryTimes - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    private LoginResponse login(){
        LoginRequest request = new LoginRequest();
        request.setUserId(9527L);
        BaseResponse<LoginResponse> response = gateService.login(request);

        return response.getData();
    }

    @PreDestroy
    public void destroy(){
        if(workerGroup != null && !workerGroup.isShutdown()){
            workerGroup.shutdownGracefully();
        }
    }
}
