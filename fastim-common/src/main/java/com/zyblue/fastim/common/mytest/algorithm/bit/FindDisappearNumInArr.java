package com.zyblue.fastim.common.mytest.algorithm.other;

/**
 * 数组元素在 0-n 之间，但是有一个数是缺失的，要求找到这个缺失的数。
 *
 * 思路：利用异或来解决，异或运算满足结合律，二进制相同数字异或为0，不同为1
 * 这道题可以利用数组下表和数字异或来做
 */
public class FindDisappearNumInArr {
    public int missingNumber(int[] nums) {
        // 求出余下的下标值和剩下数组中没有被异或的值的异或结果
        int ret = 0;
        for (int i = 0; i < nums.length; i++) {
            ret = ret ^ i ^ nums[i];
        }
        // 求出上述结果再次异或数组大小（也就是最大的那个数），最终得到缺失的下标值，也就是要求出的值
        return ret ^ nums.length;
    }
}
