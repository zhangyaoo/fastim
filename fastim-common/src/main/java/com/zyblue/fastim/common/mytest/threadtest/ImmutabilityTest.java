package com.zyblue.fastim.common.mytest.threadtest;

/**
 * @author will
 * @date 2021/10/26 16:18
 * 享元模式，本质上是对象池，不适合做锁
 * 其实基本上所有的基础类型的包装类都不适合做锁，因为它们内部用到了享元模式
 */
public class ImmutabilityTest {

    /**
     *  al 和 bl 是一个对象，结果 A 和 B 共用的是一把锁
     */
    class A {
        Long al=Long.valueOf(1);
        public void setAX(){
            synchronized (al) {
                //省略代码无数
            }
        }
    }
    class B {
        Long bl=Long.valueOf(1);
        public void setBY(){
            synchronized (bl) {
                //省略代码无数
            }
        }
    }
}
