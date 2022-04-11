package com.zyblue.fastim.common.mytest.algorithm.tengxun;

/**
 * @author will
 * @date 2022/1/21 11:00
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 *
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * 示例 1：
 *
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 *
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 *
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 *
 * 输入：x = 0
 * 输出：0
 *
 * 解决1：利用字符串解决
 * 解决2：利用取余和取除解决
 */
public class IntReverse {
    public int reverse(int x) {
        long n = 0;
        while (x != 0){
            int i = x % 10;
            n = n*10 + i;
            x = x / 10;
        }
        return (int)n==n? (int)n:0;
    }

}
