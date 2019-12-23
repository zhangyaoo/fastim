package com.zyblue.fastim.common.algorithm.string;

import java.util.HashMap;

/**
 * 两个整数字符串相加，要求不能转换为整数，返回值还是字符串
 * Author : BlueSky 2019.12.18
 *
 * 思路：
 * 1、如果两个字符串长度不同，那么长度小的用0来补齐高位
 * 2、用%10来放当前位，/10来补齐上一位
 */
public class TowIntStrAdd {
    public static String add(String str1, String str2){
        if(str1.length() > str2.length()){
            String temp = "";
            for(int i = 0;i<str1.length() - str2.length();i++){
                temp = temp + "0";
            }
            str2 = temp + str2;
        }
        if(str1.length() < str2.length()){
            String temp = "";
            for(int i = 0;i<str2.length() - str1.length();i++){
                temp = temp + "0";
            }
            str1 = temp + str1;
        }

        System.out.println(str1);
        System.out.println(str2);

        int[] ints = new int[str1.length()+1];

        for(int i = str1.length();i>0;i--){
            int a = Integer.valueOf(str1.charAt(i-1) + "");
            int b = Integer.valueOf(str2.charAt(i-1) + "");
            int tempInt = a+b+ints[i];
            ints[i] = tempInt%10;
            ints[i-1] = tempInt/10;
        }

        StringBuffer res = new StringBuffer();
        for(int i = 0; i<ints.length; i++) {
            if(i==0 && ints[i]==0){
                continue;
            }
            res.append(ints[i]);
        }

        return res.toString();
    }

    public static void main(String[] args) {
        String str1 = "743129";
        String str2 = "443123";
        System.out.println(str2.charAt(5));


        System.out.println(add(str1,str2));
    }
}
