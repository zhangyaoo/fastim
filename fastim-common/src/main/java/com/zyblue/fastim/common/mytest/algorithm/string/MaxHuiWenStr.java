package com.zyblue.fastim.common.mytest.algorithm.string;

/**
 * @author will
 * @date 2021/12/29 10:32
 * 最大的回文串 奇数 偶数
 *
 * 示例 1：
 *
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 示例 2：
 *
 * 输入：s = "cbbd"
 * 输出："bb"
 * 示例 3：
 *
 * 输入：s = "a"
 * 输出："a"
 * 示例 4：
 *
 * 输入：s = "ac"
 * 输出："a"
 */
public class MaxHuiWenStr {
    /**
     * 由于存在奇数的字符串和偶数的字符串，所以我们需要从一个字符开始扩展，或者从两个字符之间开始扩展
     * 要么从一个字符串扩展，要么从两个相邻的字符串扩展. 这样的好处就是忽略字符串是奇数还是偶数
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        // 要切割字符串的index
        int start = 0, end = 0;
        int len = 0;
        for (int i = 0; i < s.length(); i++) {
            // 一个字符串扩展
            int oddLen = max(s, i, i);
            // 两个相邻的字符串扩展
            int evenLen = max(s, i, i+1);
            // 当index为i的时候回文长度
            int lenTemp = Math.max(oddLen, evenLen);
            if(lenTemp >= len){
                len = lenTemp;
                start = i - lenTemp/2;
                end = i + lenTemp/2;
            }
        }
        return s.substring(start, end);
    }

    /**
     * left到right的最大长度
     */
    private int max(String s, int left, int right){
        int length = s.length();
        while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return right - left - 1;
    }
}
