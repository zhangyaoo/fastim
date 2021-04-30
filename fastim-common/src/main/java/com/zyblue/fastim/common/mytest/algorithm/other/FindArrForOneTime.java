package com.zyblue.fastim.common.mytest.algorithm.other;

/**
 * 找出数组中唯一的一个出现一次数字的,并且其他数字都只出现了两次
 * Author : BlueSky 2019.11.05
 * 思路与或运算
 *      int a = 5; // 0000 0101
 *     int b = 3; // 0000 0011
 *     a ^= b; // 0000 0110
 */
public class FindArrForOneTime {

    public static int find(int[] arr){
        int val = 0;

        for(int i=0; i<arr.length; i++)
        {
            val ^= arr[i];
        }

        return val;
    }

    public static void main(String[] args) {
        int[] ints = new int[8];
        ints[0] = 5;
        ints[1] = 3;
        ints[2] = 2;
        ints[3] = 2;
        ints[4] = 3;
        ints[5] = 7;
        ints[6] = 7;


        int i = find(ints);
        System.out.println("i:" + i);
    }
}
