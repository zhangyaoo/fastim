package com.zyblue.fastim.common.mytest.algorithm.thread;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author will
 * @date 2021/10/29 17:31
 * 一个任务，可以允许多个线程同时执行，但是一旦有一个线程执行完成后，其他的线程就立马终止这个任务，并且后续的线程都无法执行这个任务
 * 优雅停止解决：
 * 1)一个是仅检查终止标志位是不够的，因为线程的状态可能处于休眠态；
 * 2)另一个是仅检查线程的中断状态也是不够的，因为我们依赖的第三方类库很可能没有正确处理中断异常。
 */
public class InterruptModel {

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
                while (!finish && !Thread.currentThread().isInterrupted()){
                    try {
                        runTask();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        continue;
                    }
                    // 最开始的线程先设置为终止，其他线程则判断是否直接退出
                    if(finish){
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
