package com.zyblue.fastim.common.algorithm;

public class MultiThread {
    public synchronized void test1(){
        try{
            System.out.println("test1 begin wait");
            Thread.sleep(5000);
            System.out.println("test1 begin end");
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public synchronized void test2(){
        try{
            System.out.println("test2 begin wait");
            Thread.sleep(5000);
            System.out.println("test2 begin end");
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
}
