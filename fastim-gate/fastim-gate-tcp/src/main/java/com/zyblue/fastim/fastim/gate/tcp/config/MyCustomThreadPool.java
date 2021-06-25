package com.zyblue.fastim.fastim.gate.tcp.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.threadpool.ThreadPool;

import java.util.concurrent.Executor;

/**
 * @author will
 * @date 2021/6/24 13:04
 */
@Activate
public class MyCustomThreadPool implements ThreadPool {

    private NioEventLoopGroup workerGroup;

    public void setNioEventLoopGroup(NioEventLoopGroup nioEventLoopGroup) {
        this.workerGroup = nioEventLoopGroup;
    }

    @Override
    public Executor getExecutor(URL url) {
        return workerGroup;
    }
}
