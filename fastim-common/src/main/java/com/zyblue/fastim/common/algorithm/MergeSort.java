package com.zyblue.fastim.common.algorithm;

import java.util.Arrays;

/**
 * 归并排序
 * Author : BlueSky 2019.11.07
 * 思路:递归
 * 1、写出递推公式：merge_sort(p…r) = merge(merge_sort(p…q), merge_sort(q+1…r))
 * 2、判断终止条件：head >= tail
 * 3、分治递归
 * 4、排序后，合并两个有序数组
 */
public class MergeSort {
    public static void main(String[] args) {
        int capacity = 9;
        Integer[] arr = new Integer[capacity];
        arr[0] = 4;
        arr[1] = 1;
        arr[2] = 7;
        arr[3] = 3;
        arr[4] = 0;
        arr[5] = 5;
        arr[6] = 6;
        arr[7] = 2;
        arr[8] = 8;

        mergeSortMethod(arr, capacity);
        //System.out.println(Arrays.toString(arr));
    }

    public static void mergeSortMethod(Integer[] arr, int capacity){
        sort(arr, 0, capacity-1);
    }

    public static void sort(Integer[] arr, int head, int tail){
        if(head >= tail){
            return;
        }

        int center = (head + tail)/2;

        // 分治递归
        sort(arr, head, center);
        sort(arr, center + 1 ,tail);

        // 赋新的数组
        Integer[] arrHead = new Integer[center - head + 1];
        for(int i = 0; i < center - head + 1; i++){
            arrHead[i] = arr[head + i];
        }
        Integer[] arrTail = new Integer[tail - center];
        for(int i = 0; i < tail - center; i++){
            arrTail[i] = arr[center + i +1];
        }

        // 将arrHead和arrTail合并到arr中
        merge(arr, arrHead, head, arrTail, tail);
    }

    /**
     * 合并
     * arr:原始数组
     * arrHead：左边数组
     * head：左边数组左指针
     * arrTail：右边数组
     * tail：右边数组右指针
     */
    public static void merge(Integer[] arr, Integer[] arrHead, int head, Integer[] arrTail, int tail){
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arrHead));
        System.out.println(Arrays.toString(arrTail));
        int i = 0;
        int j = 0;
        for(int k = head;k < tail; k++){
            System.out.println("==>"+arrHead[i]);
            System.out.println("==>"+arrTail[j]);
            if(arrHead[i] <= arrTail[j]){
                arr[k] = arrHead[i];
                i++;
            }else {
                arr[k] = arrTail[j];
                j++;
            }
        }
    }
}
