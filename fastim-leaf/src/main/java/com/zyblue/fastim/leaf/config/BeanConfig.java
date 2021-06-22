package com.zyblue.fastim.leaf.config;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
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
    public CuratorFramework buildCurator(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString(config.getZkAddr())
                .sessionTimeoutMs(config.getTimeout().intValue())
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .namespace("fastIM")
                .build();
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public ScheduledExecutorService buildThreadPool(){
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("ScheduledThreadPool-%d")
                .build();
        return new ScheduledThreadPoolExecutor(1, threadFactory);
    }
}
