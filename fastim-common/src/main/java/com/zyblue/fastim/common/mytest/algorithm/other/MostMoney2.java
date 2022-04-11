package com.zyblue.fastim.common.mytest.algorithm.other;

/**
 * @author will
 * @date 2022/2/22 18:08
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 *
 */
public class MostMoney2 {

    public int maxProfit(int[] prices) {

        // 动态规划 前i天的最大收益 = max{前i-1天的最大收益，第i天的价格-前i-1天中的最小价格}
        int min = Integer.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            if(prices[i] > min){
                res = Math.max(res, prices[i] - min);
            }else {
                min = prices[i];
            }
        }
        return res;
    }
}
