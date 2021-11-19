package com.zyblue.fastim.common.mytest.algorithm.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 最大字符串长度
 * Author : will 2019.11.05
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

    public static int maxLength2(String str){
        int length = str.length();
        int i = 0, j = 0;
        int res = 0;
        HashSet<Character> objects = new HashSet<>();
        while (i < length && j < length){
            char c = str.charAt(j);
            if(objects.contains(c)){
                objects.remove(str.charAt(i++));
            }else {
                objects.add(c);
                j++;
                res = Math.max(res, j - i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int i = maxLength2("asd524566745272dww");
        System.out.println("maxLength:" + i);

    }
}
