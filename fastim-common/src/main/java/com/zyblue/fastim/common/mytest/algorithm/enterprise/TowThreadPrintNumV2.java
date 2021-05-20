package com.zyblue.fastim.common.mytest.algorithm.enterprise;

import java.util.ArrayList;
import java.util.List;

/**
 * @author will
 * @date 2020/11/3 13:01
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 * 用wait和notify实现
 */
public class TowThreadPrintNumV2 {
    private static  Integer NUM = 1;

    private static  volatile Boolean flag = true;

    private static Object LOCK = new Object();


    private static List<Integer> list1 = new ArrayList();
    private static List<Integer> list2 = new ArrayList();

    /*public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (NUM <= 10){
                    synchronized(LOCK){
                        LOCK.notify();

                        System.out.println("thread1 add num " + NUM);
                        list1.add(NUM);
                        NUM++;

                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (NUM <= 10){
                    synchronized(LOCK){
                        LOCK.notify();

                        System.out.println("thread2 add num " + NUM);
                        list1.add(NUM);
                        NUM++;

                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
    }*/
}
