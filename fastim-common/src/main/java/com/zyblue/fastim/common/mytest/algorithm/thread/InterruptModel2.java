package com.zyblue.fastim.common.mytest.algorithm.thread;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author will
 * @date 2022/2/9 18:52
 */
public class InterruptModel2 {
    private volatile boolean finish = false;

    private void runTask() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " runTask start");
        Random random = new Random();
        Thread.sleep(random.nextInt(10000));
        System.out.println(Thread.currentThread().getName() + " runTask end");
    }

    public void run(){
        ArrayList<Thread> threads = Lists.newArrayList();
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                while (!finish){
                    try {
                        runTask();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " runTask success");
                    finish = true;
                    for (int i1 = 0; i1 < threads.size(); i1++) {
                        // 判断是哪个线程正常跑完，其他线程则进行interrupt
                        if(i1 != finalI){
                            threads.get(i1).interrupt();
                        }
                    }
                }
                countDownLatch.countDown();
            });
            threads.add(thread);
        }

        threads.forEach(Thread::start);
        try {
            System.out.println(" await 。。。");
            countDownLatch.await();
            System.out.println(" await end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new InterruptModel().run();
    }
}
