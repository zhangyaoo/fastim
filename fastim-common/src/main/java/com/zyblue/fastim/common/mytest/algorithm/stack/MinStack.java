package com.zyblue.fastim.common.mytest.algorithm.stack;

import java.util.Stack;

/**
 * @author will
 * @date 2022/2/7 16:16
 * 求栈的最小值，要求getMin方法在O(1)的时间复杂度：
 *          解法：辅助栈
 * 进阶：求O(1)的时间复杂度和O(1)的空间复杂度：
 *          解法：栈中存栈顶和最小值的差值
 *
 * 入栈，存入差值（元素和最小值）入栈，并且更新最小值
 * 出栈，如果栈的元素小于0，那么先弹出栈顶，后更新最小元素；如果大于0，那么直接弹出元素和最小值的和
 */
public class MinStack {

    Stack<Integer> stack = new Stack<>();

    Integer min = null;

    public void push(Integer data){
        if (min == null){
            min = data;
        }
        stack.push(data - min);
        min = Math.min(min, data);
    }

    public Integer pop(){
        Integer diff = stack.pop();
        if(diff > 0){
            return diff + min;
        }else {
            Integer res =  min;
            min = min - diff;
            return res;
        }
    }

    public Integer getMin(){
        return min;
    }
}
