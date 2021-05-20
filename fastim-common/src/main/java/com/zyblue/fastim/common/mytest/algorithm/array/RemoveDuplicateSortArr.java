package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * 移除排序数组中重复的值，返回移除后的大小
 * Author : BlueSky 2019.11.05
 * exe：
 * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
 * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
 *
 * 思路：利用双指针
 */
public class RemoveDuplicateSortArr {
    public static int remove(int[] arr){
        if (arr.length == 0){
            return 0;
        }
        int pre = 0, cur = 0, n = arr.length;
        while (cur < n) {
            if (arr[pre] != arr[cur]){
                ++pre;
                arr[pre] = arr[cur];
            }
            cur++;
        }
        return pre + 1;
    }

    /*public static void main(String[] args) {
        int[] ints = new int[10];
        ints[0] = 0;
        ints[1] = 0;
        ints[2] = 1;
        ints[3] = 1;
        ints[4] = 1;
        ints[5] = 2;
        ints[6] = 2;
        ints[7] = 3;
        ints[8] = 3;
        ints[9] = 4;

        int remove = remove(ints);

        System.out.println(remove);

        //-new ReentrantLock()
    }*/
}
