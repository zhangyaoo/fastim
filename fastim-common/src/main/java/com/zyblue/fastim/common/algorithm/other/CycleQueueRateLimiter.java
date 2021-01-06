package com.zyblue.fastim.common.algorithm.other;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2020/11/19 15:04
 * 环形队列实现 滑动窗口限流算法
 *
 *
 */
public class CycleQueueRateLimiter {
    /**
     * 数组
     */
    private final Object[] array;

    /**
     * 容量
     */
    private Integer capacity = 10;

    /**
     * 时间窗口值
     */
    private Integer windowTime = 1;
    /**
     * 时间窗口单位
     */
    private Integer TimeUnit = 1;

    /**
     * 头指针
     */
    private Integer head = 0;

    /**
     * 尾指针
     */
    private Integer tail = 0;

    public CycleQueueRateLimiter(Integer capacity, long windowTime, TimeUnit unit) {
        this.array = new Object[capacity];

        this.capacity = capacity;
    }

    public CycleQueueRateLimiter() {
        this.array = new Object[capacity];
    }

    public boolean add(Object data){
        int i = (tail + 1) % capacity;
        if( i == head){
            return false;
        }
        // 赋值
        array[tail] = data;
        // 尾指针复位
        tail = i;
        return true;
    }

    public Object poll(){
        if(tail.equals(head)){
            return null;
        }
        // 取值
        Object res = array[head];
        // 头指针复位
        head = (head + 1) % capacity;
        return res;
    }
}
