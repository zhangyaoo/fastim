package com.zyblue.fastim.common.mytest.algorithm.enterprise;

import java.util.Stack;

/**
 * @author will.zhang
 * @date 2020/6/5 11:25
 *
 * 大宇无限面试真题
 * 实现可以求栈中最大最小值的栈，需要支持 pop，push，max，min 四个方法。
 * 要求自己实现数据结构，要求所有 function 请求的时间复杂度是 O(1)，整体空间复杂度是 O(n)
 * 实现程序前请尽可能的描述清楚思路
 */
public class StackMaxMinValue {

    private Stack<Integer> stack;

    private Stack<Integer> maxStack;

    public void push(Integer value) {
        if(value == null){
            return;
        }
        Integer peek = maxStack.peek();
        if(peek == null || peek <= value){
            maxStack.push(value);
        }
        stack.push(value);
    }

    public Integer pop() {
        Integer pop = stack.pop();
        if(pop == null){
            return null;
        }
        Integer peek = maxStack.peek();
        if(peek.equals(pop)){
            maxStack.pop();
        }
        return pop;
    }

    public Integer getMaxValue() {
        return maxStack.peek();
    }
    public Integer getMinValue() {
        return 0;
    }

}
