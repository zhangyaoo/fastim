package com.zyblue.fastim.common.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 最大字符串长度
 * Author : BlueSky 2019.11.05
 * exe:  asddww  asd 3         pwwkew wke 3
 */
public class MaxStrLength {
    public static int maxLength(String str){
        int res = 0;
        int len = str.length();
        // 字符串和下标的映射
        Map<Character, Integer> map = new HashMap<Character, Integer>(8);
        for(int head = 0, tail = 0;tail < len; tail++){
            // 如果窗口内遇到相同的字符
            if(map.containsKey(str.charAt(tail))){
                // 移动前指针
                head = map.get(str.charAt(tail));
            }
            // 计算窗口内的大小
            res = Math.max(res, tail - head);
            map.put(str.charAt(tail), tail);
        }

        return res;
    }

    public static void main(String[] args) {
        int i = maxLength("abcabcbb");
        System.out.println("maxLength:" + i);

    }
}
