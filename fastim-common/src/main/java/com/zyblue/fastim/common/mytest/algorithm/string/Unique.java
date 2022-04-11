package com.zyblue.fastim.common.mytest.algorithm.string;

/**
 * @author will
 * @date 2022/1/12 13:08
 * 题目：判断是否是不重复的字符串
 * 输入: s = "leetcode"
 * 输出: false
 *
 *
 * 解决：ASCII码的字符个数为128个，我们可以使用两个64位，8字节的long变量来存储是否出现某个字符，二进制位1表示出现过，0表示没有出现过。
 *
 * 前提知识：char类型自带ASCII码中第几个属性
 */
public class Unique {
    public boolean isUnique(String s) {
        long left=0;
        long right=0;
        for(char c:s.toCharArray()){
            if(c>=64){
                long bitIndex=1L <<(c-64);
                // left & bitIndex 判断left的第k位数字是0是1
                if((left & bitIndex)!=0){
                    // 代表c-64位已有相同的char
                    return   false;
                }
                // 将left的第c-64位数字赋值为1
                left |=bitIndex;
            }else{
                long bitIndex=1L <<c;
                if((right & bitIndex)!=0){
                    return false;
                }
                right |=bitIndex;
            }
        }
        return true;
    }
}
