package com.zyblue.fastim.logic.msg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class BeanConfig {

    @Bean(name = "asyncServiceExecutor")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);
        //配置最大线程数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() + 1);
        //配置队列大小
        executor.setQueueCapacity(1000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("ThreadPoolTaskExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
