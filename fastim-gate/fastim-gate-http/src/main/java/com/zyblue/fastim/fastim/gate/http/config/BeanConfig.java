package com.zyblue.fastim.fastim.gate.http.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author will
 * @date 2021/6/11 11:57
 */
@Configuration
public class BeanConfig {

    @Bean("synchronizerThread")
    public ThreadPoolTaskExecutor synchronizer(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(1);
        //配置最大线程数
        executor.setMaxPoolSize(1);
        //配置队列大小
        executor.setQueueCapacity(1000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("ConfigSynchronizer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean(value = "nioThreadPool")
    public NioEventLoopGroup group(){
        int cpuCount = Runtime.getRuntime().availableProcessors();
        return new NioEventLoopGroup(cpuCount << 1 + 1);
    }

    @Bean(value = "configService")
    public ConfigService configService() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", "127.0.0.1:8848");
        return NacosFactory.createConfigService(properties);
    }
}
