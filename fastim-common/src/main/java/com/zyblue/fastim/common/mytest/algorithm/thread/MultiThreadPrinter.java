package com.zyblue.fastim.common.mytest.algorithm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2021/10/18 16:15
 *  腾讯面试题：多线程交替打印
 *  假设有N个线程，整数M。线程交替打印，整数。
 *  举例 N = 3    M = 5
 *  结果 ：
 *  thread-1:1
 *  thread-2:2
 *  thread-3:3
 *  thread-1:4
 *  thread-2:5
 *  thread-3:1
 *  thread-1:2
 *  ...
 */
public class MultiThreadPrinter {

    public static void main(String[] args) {
        MultiThreadPrinter multiThreadPrinter = new MultiThreadPrinter(3, 5);
        try {
            multiThreadPrinter.startPrint();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final ReentrantLock lock = new ReentrantLock();

    private final List<Thread> threads = new ArrayList<>();

    private final List<Condition> conditions = new ArrayList<>();

    private final int number;

    private final int threadNums;

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public MultiThreadPrinter(int threadNums, int number){
        this.number = number;
        this.threadNums = threadNums;
        for (int i = 0; i < threadNums; i++) {
            threads.add(new Printer("thread" + (i + 1), i));
            conditions.add(lock.newCondition());
        }
    }

    public void startPrint() throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        Thread.sleep(1000);
        Condition firstCondition = conditions.get(0);
        lock.lock();
        try {
            // 通知第一个condition
            firstCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    public class Printer extends Thread{
        private final int index;
        private int nextIndex;

        public Printer(String name, int index) {
            super(name);
            this.index = index;
            // 下一个线程index,方便通知下一个线程
            nextIndex = ++index;
            if (nextIndex == threadNums) {
                nextIndex = 0;
            }
        }

        @Override
        public void run() {
            lock.lock();
            try {
                while (true) {
                    conditions.get(index).await();
                    // 打印
                    System.out.println(this.getName() + "-" + atomicInteger.get());
                    if (atomicInteger.incrementAndGet() > number) {
                        atomicInteger.set(0);
                    }
                    // 通知下一个线程
                    conditions.get(nextIndex).signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

