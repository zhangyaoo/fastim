package com.zyblue.fastim.common.mytest.algorithm.superior;

/**
 * 爬楼梯
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 动态规划
 * 1.动态规划法试图只解决每个子问题一次
 * 2.一旦某个给定子问题的解已经算出，则将其记忆化存储，以便下次需要同一个子问题解之时直接查表。
 *
 *
 */
public class ClimbStairs {

    /**
     * 时间复杂度为O(n) 空间复杂度为O(n)
     */
    public static int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }

        // 多开一位，考虑起始位置
        int[] dp = new int[n + 1];

        dp[0] = 0; dp[1] = 1; dp[2] = 2;
        for (int i = 3; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 时间复杂度为O(n) 空间复杂度为O(1)
     */
    public static int climbStairsV2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        // a 保存倒数第二个子状态数据，b 保存倒数第一个子状态数据， temp 保存当前状态的数据
        int a = 1, b = 2;
        int temp = a + b;
        for (int i = 2; i <= n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        return temp;
    }

    public static int climbStairsV3(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int n1 = 0;
        int n2 = 1;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = n1 + n2;
            n1 = n2;
            n2 = result;
        }
        return result;
    }


    /*public static void main(String[] args) {
        int n = 10;
        int i = climbStairsV2(n);
        System.out.println("i:" + i);

        System.out.println("j:" + (12 << 3));

        int[] dp = new int[n + 1];


    }*/
}
