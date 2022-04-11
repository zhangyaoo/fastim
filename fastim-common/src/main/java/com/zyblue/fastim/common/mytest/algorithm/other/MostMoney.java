package com.zyblue.fastim.common.mytest.algorithm.other;

/**
 * @author will
 * @date 2021/12/28 14:54
 * 股票买卖最佳时机
 */
public class MostMoney {

    public static void main(String[] args) {
        int[] ints = new int[5];
        ints[0] = 1;
        ints[1] = 2;
        ints[2] = 3;
        ints[3] = 4;
        ints[4] = 5;
        System.out.println(maxProfit(ints));
    }

    public static int maxProfit(int[] prices) {
        if(prices.length <= 1){
            return 0;
        }
        int maxPro = 0;
        int a = 0;
        int b = 1;
        while(b < prices.length){
            if(prices[a] >= prices[b]){
                a++;
            }else{
                while(b < prices.length - 1){
                    if(prices[b] < prices[b + 1]){
                        b++;
                    }
                }
                maxPro = maxPro + (prices[b] - prices[a]);
                a = b;
            }
            b++;
        }
        return maxPro;
    }
}
