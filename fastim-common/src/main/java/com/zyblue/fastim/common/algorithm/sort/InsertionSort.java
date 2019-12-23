package com.zyblue.fastim.common.algorithm.sort;

/**
 * 插入排序
 * 分两个区域，左边已经排序好的，右边未排序
 * 当我们需要将一个数据 a 插入到已排序区间时，需要拿 a 与已排序区间的元素依次比较大小，找到合适的插入位置。
 * 找到插入点之后，我们还需要将插入点之后的元素顺序往后移动一位，这样才能腾出位置给元素 a 插入
 */
public class InsertionSort {

    /*public static void main(String[] args) {

    }*/

    public static void insertionSortMethod(Integer[] arr, Integer n){
        if(n <= 1){
            return;
        }

        for(int i = 1;i <= n;i++){
            Integer val = arr[i];
            int j = i - 1;
            for(;j > 0;--j){
                if(arr[j] > val){
                    // 移动数组
                    arr[j + 1] = arr[j];
                }else {
                    arr[j + 1] = val;
                    break;
                }
            }
        }
    }
}
