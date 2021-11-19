package com.zyblue.fastim.common.mytest.algorithm.thread;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2020/11/18 11:20
 * 多线程事务：由于事务具有隔离性，所以按道理是不存在多线程事务
 * 要实现的功能：多线程执行数据插入，如果一条线程插入数据执行失败就回滚
 * 实现思路：利用CountDownLatch和编程式事务
 *
 *
 *
 * 注意的点：
 * 1、workerCount工作线程不是越多越好，注意和数据库最大连接数保持相近
 * 2、线程池最大核心连接数要保持和workerCount。不然程序会无法跑通
 * 3、当协调者说可以提交了，如果参与者提交commit的时候挂了，还是会出现问题
 */
public class MultiThreadTransaction {
    public static volatile boolean commit = true;

    /*public static void main(String[] args) throws InterruptedException {
        doProcess();
    }*/

    /**
     * 一不小心就实现了简单的2PC
     */
    public static void doProcess() throws InterruptedException {
        int workerCount = 5;

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(workerCount, workerCount, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), new ThreadFactoryBuilder().setNameFormat("ThreadPool-%d").build());
        CountDownLatch childDownLatch = new CountDownLatch(5);

        CountDownLatch mainDownLatch = new CountDownLatch(1);

        // 每个子线程返回结果，主线程根据结过来判断是否提交
        ArrayList<Boolean> childResponse = Lists.newArrayList();

        for (int i = 0; i < workerCount; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() ->{
                System.out.println("线程"+ finalI +"开始执行，开始事务");

                try {
                    Thread.sleep(300);
                    if(finalI ==4){
                        throw new Exception("线程"+ finalI +"数据插入异常");
                    }
                    System.out.println("数据插入成功，等待提交");
                    childResponse.add(Boolean.TRUE);
                    childDownLatch.countDown();
                    mainDownLatch.await();

                    // 根据主线程（协调者）返回的结果来提交或者回滚
                    if(commit){
                        System.out.println("数据提交");
                    }else {
                        System.out.println("数据回滚");
                    }
                }catch (Exception e){
                    System.out.println("数据插入异常");
                    childResponse.add(Boolean.FALSE);
                    childDownLatch.countDown();
                }
            });
        }
        try {
            childDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }

        boolean b = childResponse.stream().allMatch(aBoolean -> aBoolean);
        if(!b){
            commit = false;
        }
        mainDownLatch.countDown();
        //为了让主线程阻塞，让子线程执行。
        Thread.currentThread().join();
    }
}
