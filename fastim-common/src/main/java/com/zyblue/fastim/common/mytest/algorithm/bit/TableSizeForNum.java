package com.zyblue.fastim.common.mytest.algorithm.bit;

/**
 * @author will
 * @date 2020/11/18 17:04
 * hashmap的构造函数：
 * 给定一个数，返回一个大于输入参数且最小的为2的n次幂的数。
 */
public class TableSizeForNum {

    public static int tableSizeFor(int n){
        // n-1的作用是避免输入8，返回结果为16，正确结果为8
        n = n - 1;

        /*
         *  下面的作用就是让n的最高一位的1的后面，都变成1，例如 0011 0000  -》 0011 1111
         */
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        // n + 1 就是变成 0100 0000
        return n < 0 ? 1 : n + 1;
    }

    /*public static void main(String[] args) {
        final int i = tableSizeFor(8);
        System.out.println(i);
    }*/
}
