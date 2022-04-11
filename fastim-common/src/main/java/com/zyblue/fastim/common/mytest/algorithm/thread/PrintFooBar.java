package com.zyblue.fastim.common.mytest.algorithm.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2022/2/9 18:37
 */
public class PrintFooBar {

    public static void main(String[] args) {

    }

    int n = 3;

    Lock lock = new ReentrantLock(true);

    private final Condition foo = lock.newCondition();

    volatile boolean flag = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while(!flag) {
                    foo.await();
                }
                printFoo.run();
                flag = false;
                foo.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n;i++) {
            lock.lock();
            try {
                while(flag) {
                    foo.await();
                }
                printBar.run();
                flag = true;
                foo.signal();
            }finally {
                lock.unlock();
            }
        }
    }
}
