package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * @author will
 * @date 2022/2/8 11:43
 *
 * 移动零
 * 示例:
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 */
public class MoveZeroToTail {

    public void moveZeroes(int[] nums) {
        int numsSize = nums.length;

        int j = 0;
        for (int i = 0; i < numsSize; i++) {
            // 相当于将不等于0的所有元素重新排列
            if(nums[i] != 0){
                nums[j] = nums[i];
                j++;
            }
        }
        //将后面的元素全部置为0
        while (j < numsSize){
            nums[j++] = 0;
        }
    }
}
