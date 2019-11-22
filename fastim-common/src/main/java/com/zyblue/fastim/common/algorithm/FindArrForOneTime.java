package com.zyblue.fastim.common.algorithm;

/**
 * 找出数组中唯一的一个出现一次数字的
 * Author : BlueSky 2019.11.05
 * 思路与或运算
 *      int a = 5; // 0000 0101
 *     int b = 3; // 0000 0011
 *     a ^= b; // 0000 0110
 */
public class FindArrForOneTime {

    public int find(int[] arr){
        int val = 0;

        for(int i=0; i<arr.length; i++)
        {
            val ^= arr[i];
        }

        return val;
    }
}
