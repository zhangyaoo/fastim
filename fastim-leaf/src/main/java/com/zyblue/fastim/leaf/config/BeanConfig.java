package com.zyblue.fastim.leaf.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;


@Configuration
public class BeanConfig {

    @Autowired
    private ZKConfig config;

    @Bean
    public ZkClient buildZKClient(){
        return new ZkClient(config.getZkAddr(), config.getTimeout().intValue());
    }

    @Bean
    public ScheduledExecutorService buildThreadPool(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("ScheduledThreadPool-%d")
                .build();
        //int coreSize = Runtime.getRuntime().availableProcessors();
        //int maxSize = 2*coreSize + 1;
        //ThreadPoolExecutor executorService = new ThreadPoolExecutor(coreSize, maxSize, 5, TimeUnit.SECONDS, new ScheduledThreadPoolExecutor.DelayedWorkQueue(), threadFactory);
        return new ScheduledThreadPoolExecutor(1, threadFactory);
    }
}
