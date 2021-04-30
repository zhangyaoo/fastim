package com.zyblue.fastim.common.mytest.nettysourcecode;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty 简单源码
 */
public class Start {
    public static void main(String[] args) {
        //声明线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();
        //初始化线程池
        ThreadHandle threadHandle = new ThreadHandle(boss,work);
        //声明端口
        threadHandle.bind(new InetSocketAddress(9000));
        System.out.println("start");
    }
}
