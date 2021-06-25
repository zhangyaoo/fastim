package com.zyblue.fastim.fastim.gate.tcp.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author will
 * @date 2021/6/25 0:15
 */
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "nioEventLoopGroup")
    public NioEventLoopGroup workerGroup(){
        int cpuCount = Runtime.getRuntime().availableProcessors();
        return new NioEventLoopGroup(cpuCount << 1);
    }
}
