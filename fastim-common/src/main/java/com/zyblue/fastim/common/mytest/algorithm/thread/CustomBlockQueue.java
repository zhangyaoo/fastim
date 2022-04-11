package com.zyblue.fastim.common.mytest.algorithm.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2021/10/15 15:07
 * 阻塞队列
 */
public class CustomBlockQueue<E> {
    private final ReentrantLock putLock = new ReentrantLock();

    private final Condition notEmptyCondition = putLock.newCondition();

    private final ReentrantLock takeLock = new ReentrantLock();

    private final Condition notFullCondition = takeLock.newCondition();

    private  int putIndex;

    private  int takeIndex;

    private  int count;

    private final Object[] items;

    public CustomBlockQueue(int capacity) {
        this.takeIndex = 0;
        this.putIndex = 0;
        items = new Object[capacity];
    }

    public void put(E element) throws InterruptedException{
        // 先校验
        putLock.lockInterruptibly();
        try {
            // 用while不用if的原因是notEmptyCondition唤醒一个线程后，可能存在虚假唤醒的情况，为了防止其他线程跳出逻辑，则使用while来避免
            while (count >= items.length){
                notFullCondition.await();
            }
            items[putIndex++] = element;
            // 为了循环利用数组，不发生移动或者浪费内存，使用一个循环队列来存储数据
            // 因为有count的存在，putIndex和takeIndex直接暴力置为0即可
            if(putIndex == items.length){
                putIndex = 0;
            }
            count++;
            notEmptyCondition.signal();
        } finally {
            putLock.unlock();
        }
    }

    public E take() throws InterruptedException{
        // 先校验
        takeLock.lockInterruptibly();
        try {
            while (count <= 0){
                notFullCondition.await();
            }
            @SuppressWarnings("unchecked")
            E result = (E)items[takeIndex++];
            if(takeIndex == items.length){
                takeIndex = 0;
            }
            count--;
            notFullCondition.signal();
            return result;
        } finally {
            takeLock.unlock();
        }
    }
}
