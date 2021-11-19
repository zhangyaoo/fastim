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
            if(count == items.length){
                notFullCondition.await();
            }
            items[putIndex++] = element;
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
            if(items.length == 0){
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
