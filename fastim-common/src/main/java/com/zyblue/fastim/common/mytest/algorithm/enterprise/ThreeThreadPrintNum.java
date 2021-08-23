package com.zyblue.fastim.common.mytest.algorithm.enterprise;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2021/8/19 16:37
 *
 * 线程同步问题 三个线程A、B、C，A线程打印10次字母A，B线程打印10次字母B,C线程打印10次字母C，
 * 但是要求三个线程同时运行，并且实现交替打印，即按照ABCABCABC的顺序打印
 */
public class ThreeThreadPrintNum {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        Thread threadA = new Thread(() ->{
            int num = 10;
            while (num > 0){
                lock.lock();
                System.out.println("A加锁成功");
                try {
                    conditionA.await();
                    System.out.println("A");
                    conditionB.signal();
                    conditionA.await();
                    num--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("A释放锁");
                    lock.unlock();
                }

            }
        });
        threadA.start();

        Thread threadB = new Thread(() ->{
            int num = 10;
            while (num > 0){
                lock.lock();
                System.out.println("B加锁成功");
                try {
                    conditionB.await();
                    System.out.println("B");
                    conditionC.signal();
                    conditionB.await();
                    num--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("B释放锁");
                    lock.unlock();
                }

            }
        });
        threadB.start();

        Thread threadC = new Thread(() ->{
            int num = 10;
            while (num > 0){
                lock.lock();
                System.out.println("C加锁成功");
                try {
                    System.out.println("C");
                    conditionA.signal();
                    conditionC.await();
                    num--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("C释放锁");
                    lock.unlock();
                }

            }
        });
        threadC.start();
        threadA.join();
        threadB.join();
        threadC.join();

    }
}










