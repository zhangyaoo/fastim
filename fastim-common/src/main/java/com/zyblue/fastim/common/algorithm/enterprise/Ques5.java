package com.zyblue.fastim.common.algorithm.enterprise;

import org.apache.commons.lang3.StringUtils;

/**
 * @author will.zhang
 * @date 2020/6/5 11:38
 *
 * 乐信面试真题
 *
 * 把一个字符串的大写字母放到字符串的后面，各个字符的相对位置不变，且不能申请额外的空间。
 * 示例：输入：AkleBiCeilD 输出：kleieilABCD
 */
public class Ques5 {
    public static String transfer(String text){
        if(StringUtils.isBlank(text)){
            return "";
        }
        int length = text.length();
        int tail = 0;
        int head = 0;

        boolean upperCase = isUpperCase(text.charAt(tail));
        //while ()

        return null;
    }

    public static boolean isUpperCase(char c) {
        return c >=65 && c <= 90;
    }

    public static void main(String[] args) {
        System.out.println(transfer("AkleBiCeilD"));
    }
}
