package com.zyblue.fastim.common.mytest.algorithm.other;

/**
 * 实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 *
 * 思路：双指针
 */
public class AdjustOddEvenOrder {


    public static int[] reOrderArray(int[] array) {
        int head = 0;
        int tail = array.length-1;

        while(tail-head<=1){
            int headVal = array[head];
            int tailVal = array[tail];

            if(headVal >> 1 << 1 != headVal){

            }
        }


        return array;
    }

    public static void main(String[] args) {
        int[] ints = new int[8];
        System.out.println("i:" + reOrderArray(ints));
    }
}
