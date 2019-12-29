package com.zyblue.fastim.common.algorithm.other;

/**
 * 找出数组中奇数个的数字，时间和空间复杂度最优
 * 思路：位异或运算满足结合律，偶数个异或结果是0，奇数个异或结果是本身
 */
public class FindArrOddNum {
    public static int find(int[] arr){
        int val = 0;

        for(int i=0; i<arr.length; i++)
        {
            val ^= arr[i];
        }

        return val;
    }

    public static void main(String[] args) {
        int[] ints = new int[7];
        ints[0] = 5;
        ints[1] = 3;
        ints[2] = 2;
        ints[3] = 2;
        ints[4] = 3;
        ints[5] = 5;
        ints[6] = 5;

        int i = find(ints);
        System.out.println("i:" + i);

        int i1 = 4 >> 1 << 1;
        System.out.println("i:" + i1);

    }
}
