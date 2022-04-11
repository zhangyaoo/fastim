package com.zyblue.fastim.common.mytest.algorithm.array;

import java.util.List;

/**
 * @author will
 * @date 2022/2/10 19:05
 * 三数字之和为0
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 */
public class ThreeSumZero {
    public List<List<Integer>> threeSum(int[] nums) {
        //1、特殊判断null和size
        //2、先排序
        //3、循环排序后的数组
        //4、如果num[i] > 0 那么就可以结束
        //5、如果num[i]=num[i+1]那么直接continue跳过，以避免重复数据
        //6、定义两个指针第一个指针为L=i+1，第二个指针为R=size-1，后续就可以循环
        //7、如果3个数和为0，那么，加入到结果中，并且向里方向同时移动前后指针，执行下一个循环。并且判断num[L]=num[L+1]是否相等需跳过去重
        //8、如果和大于0，那么向左移动右指针；小于0那么向右移动左指针

        //时间复杂度为O(N2)+N*O(logN)
        return null;
    }
}
