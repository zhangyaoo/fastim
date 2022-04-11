package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * @author will
 * @date 2022/2/7 18:05
 * 求最大的连续子数组和
 * <p>
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * <p>
 * 最优解：当我们加上一个正数时，和会增加；当我们加上一个负数时，和会减少。
 * 如果当前得到的和是个负数，那么这个和在接下来的累加中应该抛弃并重新清零，不然的话这个负数将会减少接下来的和
 */
public class MinSubArraySum {
    public int maxSubArray(int[] nums) {
        int current = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (current < 0) {
                //当前数小于0 肯定会舍去（否则将会影响接下来的和），换为下一个数
                current = nums[i];
            } else {
                //如果当前数不小于0，那么他会对接下来的和有积极影响
                current += nums[i];
            }
            sum = Math.max(current, sum);
        }
        return sum;
    }
}
