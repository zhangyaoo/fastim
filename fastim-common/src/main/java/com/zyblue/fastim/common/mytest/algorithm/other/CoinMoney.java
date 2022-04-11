package com.zyblue.fastim.common.mytest.algorithm.other;

import java.util.Arrays;

/**
 * @author will
 * @date 2021/12/16 12:06
 */
public class CoinMoney {
    public int coinChange(int[] coins, int amount) {
        // 动态规划，自底而上计算
        // f(11) = f(10) + 1; f(11) = f(9) + 1; f(11) = f(6) + 1;
        // 这里的1指的是1个硬币
        // 推出公式 f(amt) = min(f(amt-coins[0]) + f(amt-coins[1] + f(amt-coins[coins.length -1]))) + 1;

        // int[i] 代表，金额为i的时候最小的硬币数量
        int[] res = new int[amount + 1];
        res[0] = 0;
        res[1] = 1;
        for(int i = 1;i<res.length;i++){
            // 假设最少要MAX_VALUE个
            int min = Integer.MAX_VALUE;
            for(int j = 0;j<coins.length;j++){
                //i-coins[j] > 0 代表减去面额后如果还有剩余金额
                if(i-coins[j] > 0){
                    min = Math.min(min, res[i-coins[j]] + 1);
                }
            }
            res[i] = min;
        }
        return res[amount];
    }

    public int coinChange1(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
