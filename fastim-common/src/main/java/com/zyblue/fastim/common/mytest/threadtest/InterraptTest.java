package com.zyblue.fastim.common.mytest.threadtest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2021/10/26 11:55
 * 测试线程中断场景
 */
public class InterraptTest {

    /**
     * 可能会出现活锁的代码
     * 解释：活锁就是指线程一直处于运行（RUNNABLE）状态，但却是在做无用功，而这个线程本身要完成的任务却一直无法进展
     * 原因：没有引入随机性，导致线程在释放锁后马上又重新去获取，而此时另外的线程可能也正在这样做，互相又都获取不到锁。
     */
    class Account {
        private int balance;
        private final Lock lock
                = new ReentrantLock();
        // 转账
        void transfer(Account tar, int amt){
            while (true) {
                if(this.lock.tryLock()) {
                    try {
                        if (tar.lock.tryLock()) {
                            try {
                                this.balance -= amt;
                                tar.balance += amt;
                            } finally {
                                tar.lock.unlock();
                            }
                        }//if
                    } finally {
                        this.lock.unlock();
                    }
                }//if
            }//while
        }//transfer
    }

    /**
     *
     * 抛出InterruptedException异常，而这个抛出这个异常会清除当前线程的中断标识
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            Thread th = Thread.currentThread();
            while (!th.isInterrupted()) {
                // 省略业务代码无数
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("thread interapt");
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println(th.isInterrupted());
            }
            System.out.println("test end");
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        thread.join();
    }
}
