package com.zyblue.fastim.common.mytest.algorithm.string;


/**
 * @author will.zhang
 * @date 2020/6/5 11:38
 *
 * 乐信面试真题
 *
 * 把一个字符串的大写字母放到字符串的后面，各个字符的相对位置不变，且不能申请额外的空间。
 * 要求时间复杂度为O(N)
 * 示例：输入：AkleBiCeilD 输出：kleieilABCD
 */
public class UpperRightLowerLeftString {
    public static String transfer(String text){
        if(text.length() == 0){
            return "";
        }

        char[] chars = text.toCharArray();
        int length = chars.length;
        int tail = 0;
        int head = 1;
        while (head <= length -1){
            boolean upperCaseHead = isUpperCase(chars[head]);
            boolean upperCaseTail = isUpperCase(chars[tail]);
            if(upperCaseHead == upperCaseTail){
                head++;
            }else {
                char tempChar = chars[head];
                int tempInt = head;
                while (tempInt > tail){
                    chars[tempInt] = chars[tempInt-1];
                    tempInt--;
                }
                chars[tail] = tempChar;
                head++;
                tail++;
            }
        }

        return String.valueOf(chars);
    }

    private static boolean isUpperCase(char c) {
        return c >=65 && c <= 90;
    }

    /*public static void main(String[] args) {
        System.out.println(transfer("AkleBiCeilD"));
    }*/
}
