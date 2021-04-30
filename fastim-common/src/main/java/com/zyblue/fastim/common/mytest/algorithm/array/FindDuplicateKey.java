package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * 数据中寻找重复数
 * 示例 1:
 * 输入: [1,3,4,2,2]
 * 输出: 2
 *
 * 示例 2:
 * 输入: [3,1,3,4,2]
 * 输出: 3
 *
 * 限制：
 * 不能更改原数组（假设数组是只读的）。
 * 只能使用额外的 O(1) 的空间。
 * 时间复杂度小于 O(n2) 。
 * 数组中只有一个重复的数字，但它可能不止重复出现一次。
 *
 * 思路：
 * 第一种：我认为是最简单粗暴的一种方式，时间复杂度最差情况下刚好是O(n^2)，也就是循环遍历数组，分别比对每个元素，在数组中是否存在相同的元素，如果存在相同的元素，则立即返回。
 *
 * 第二种：先排序，排序之后，相同的元素必然处于相邻的位置，那么直接遍历一次即可（当然这个方法存在利用额外的空间，空间复杂度可能不是O(1)）
 *
 * 第三种：利用Set集合，不过也很明显使用了额外空间。
 */
public class FindDuplicateKey {

    public class Solution {
        public int findDuplicate(int[] nums) {
            for(int i = 0; i < nums.length; i++){
                for(int j = i + 1; j < nums.length; j++){
                    if(nums[i] == nums[j]) {
                        return nums[i];
                    }
                }
            }
            return 0;
        }
    }
}
