package com.zyblue.fastim.common.algorithm.other;

/**
 * 二进制中1的个数
 */
public class BinaryTimesFor2 {
    public static int times(int n){
        int res = 0;
        while (n != 0){
            if((n & 1) == 1){
                res++;
            }
            n = n >> 1;
        }
        return res;


    }

    public static void main(String[] args) {
        System.out.println("i:" + times(255));
    }
}
