package com.zyblue.fastim.common.mytest.nettysourcecode;

import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadHandle {
    public final AtomicInteger bossIndex = new AtomicInteger();
    public static NettyBoss[] bosses;
    public final AtomicInteger workerIndex = new AtomicInteger();
    public static NettyWork[] workeres;

    public ThreadHandle(ExecutorService boss, ExecutorService work) {
        bosses = new NettyBoss[1];
        //初始化boss线程池
        for (int i = 0; i < bosses.length; i++) {
            bosses[i] = new NettyBoss(boss,this);
        }
        workeres = new NettyWork[Runtime.getRuntime().availableProcessors() * 2];
        //初始化work线程池
        for (int i = 0; i < workeres.length; i++) {
            workeres[i] = new NettyWork(work);
        }
    }

    public void bind(InetSocketAddress inetSocketAddress) {
        try {
            // 获得一个ServerSocket通道
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            // 设置通道为非阻塞
            serverChannel.configureBlocking(false);
            // 将该通道对应的ServerSocket绑定到port端口
            serverChannel.socket().bind(inetSocketAddress);
            //获取一个boss线程
            NettyBoss nextBoss = bosses[Math.abs(bossIndex.getAndIncrement() % workeres.length)];
            //向boss注册一个ServerSocket通道
            Runnable runnable = () -> {
                try {
                    //注册serverChannel到selector
                    serverChannel.register(nextBoss.selector, SelectionKey.OP_ACCEPT);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            };
            //加入任务队列
            nextBoss.taskQueue.add(runnable);
            if (nextBoss.selector != null) {
                //排除其他任务处理
                if (nextBoss.wakenUp.compareAndSet(false, true)) {
                    //放开阻塞
                    nextBoss.selector.wakeup();
                }
            } else {
                //移除任务
                nextBoss.taskQueue.remove(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
