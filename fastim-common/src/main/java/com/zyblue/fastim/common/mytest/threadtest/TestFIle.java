package com.zyblue.fastim.common.mytest.threadtest;

/**
 * @author will
 * @date 2021/10/18 12:02
 * 多线程对同一个变量进行改变，为什么小于实际值？
 * 因为有CPU多级缓存
 */
public class TestFIle {


    public static int value = 0;

    static class  Adder extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 5000000; i++) {
                value =value + 1;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Adder adder11 = new Adder();
        Adder adder22 = new Adder();
        adder11.start();
        adder22.start();
        //adder11.join();
        adder22.join();
        Thread.sleep(2000);
        System.out.println(value);
    }
}