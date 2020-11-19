package com.zyblue.fastim.common.algorithm.enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author will
 * @date 2020/11/3 12:11
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 */
public class TowThreadPrintNum {

    private static  Integer NUM = 1;

    private static  volatile Boolean flag = true;

    private static  ReentrantLock LOCK = new ReentrantLock();

    private static List<Integer> list1 = new ArrayList();
    private static List<Integer> list2 = new ArrayList();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (NUM <= 100){
                    if(flag){
                        /*try {
                            LOCK.lock();
                            System.out.println("thread1 add num " + NUM);
                            list1.add(NUM);
                            NUM++;
                        } finally {
                            LOCK.unlock();
                        }
                        flag = false;*/
                        System.out.println("thread1 add num " + NUM);
                        list1.add(NUM);
                        NUM++;
                        flag = false;
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (NUM <= 100){
                    if(!flag) {
                        /*try {
                            LOCK.lock();
                            System.out.println("thread2 add num " + NUM);
                            list2.add(NUM);
                            NUM++;
                        } finally {
                            LOCK.unlock();
                        }
                        flag = true;*/
                        System.out.println("thread2 add num " + NUM);
                        list2.add(NUM);
                        NUM++;
                        flag = true;
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("list1:" + list1);
        System.out.println("list2:" + list2);
    }


}
