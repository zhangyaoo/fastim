package com.zyblue.fastim.common.mytest.algorithm.enterprise;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/3/4 10:48
 *
 * 滑动窗口限流算法 环形队列实现
 * 用滑动窗口来限流时，设置的单位时间越小，分割的时间越多，统计就会越准确
 */
public class SlidingWindowLimitingCircleQueue {
    private Object[] element;

    private int size = 10;

    /**
     * 前指针
     */
    private int next;

    /**
     * 后指针
     */
    private int prev;

    private synchronized boolean add(Object o){
        if(element == null){
            element = new Object[size];
        }
        int i = (next + 1) % size;
        if(i == prev){
            return false;
        }
        element[next] = o;
        // 环形队列指针复位
        next = i;
        return true;
    }

    private synchronized Object poll(){
        if(next == prev){
            return null;
        }
        Object o = element[prev];
        // 环形队列指针复位
        prev = (prev + 1) % size;
        return o;
    }


    /**
     * 窗口时间段
     */
    private int seconds;

    /**
     * 窗口最大数
     */
    private int limits;

    /**
     * 当前窗口的总数
     */
    private int currentLimits;

    /**
     * 窗口的头
     */
    private int head;

    /**
     * 窗口的尾
     */
    private int tail;

    private TimeUnit unit;

    /**
     * @param seconds 多长时间内
     * @param limits 允许的限流并发数
     */
    public SlidingWindowLimitingCircleQueue(int seconds, int limits, TimeUnit unit){
        this.seconds = seconds;
        this.limits = limits;
        this.size = seconds + 5;
        this.element = new Object[seconds + 5];
        this.currentLimits = 0;
        this.tail = 0;
        this.head = seconds;
        this.unit = unit;
        this.init();
    }

    /**
     * 定时任务 滑动
     */
    private void init(){
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setDaemon(true);
        threadFactoryBuilder.setNameFormat("slideWindow-pool-");

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, threadFactoryBuilder.build());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::slideWindow, 0, 1, unit);
    }

    private void slideWindow(){
        this.head = (head + 1) % size;
        element[tail] = 0;
        this.tail = (tail + 1) % size;
    }


    private synchronized boolean tryAcquire(){
        if(currentLimits >= limits){

        }

        currentLimits++;
        return false;
    }
}
