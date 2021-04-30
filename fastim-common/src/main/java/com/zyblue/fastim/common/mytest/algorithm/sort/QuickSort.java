package com.zyblue.fastim.common.mytest.algorithm.sort;

/**
 *  快速排序
 *  Author : BlueSky 2019.11.08
 *  思路：
 *  1、排序数组中下标从 p 到 r 之间的一组数据，选择 p 到 r 之间的任意一个数据作为 partitionPoint（分区点）。
 *  2、遍历 p 到 r 之间的数据，将小于 partitionPoint 的放到左边，将大于 partitionPoint 的放到右边，将 partitionPoint 放到中间。
 *  3、分治递归重复1、2步骤，递归排序下标从 p 到 q-1 之间的数据和下标从 q+1 到 r 之间的数据，直到下标间隔为1，那么所有都排序完。
 *  4、总结递归公式：quickSort(head,tail) = quickSort(head,point-1) + quickSort(point+1,tail)，写出终止条件p>=r。
 *  5、1和2分区置换操作操作可以申请新的内存空间来临时存储数据，但是这样空间复杂度会变高。使用原地排序算法来进行排序。
 *  6、使用原地排序算法在插入数组数据时候，可以使用交换swap操作，而不是迁移数组。降低插入的时间复杂度。
 */
public class QuickSort {
    /*public static void main(String[] args) {
        int capacity = 8;
        Integer[] arr = new Integer[capacity];
        arr[0] = 4;
        arr[1] = 1;
        arr[2] = 7;
        arr[3] = 3;
        arr[4] = 0;
        arr[5] = 5;
        arr[6] = 6;
        arr[7] = 2;

        mergeSortMethod(arr,capacity);
        System.out.println("==>final arr:"+ Arrays.toString(arr));
    }*/

    public static void mergeSortMethod(Integer[] arr, int capacity){
        sort(arr, 0, capacity-1);
    }

    public static void sort(Integer[] arr, int head, int tail){
        if(tail <= head){
            return;
        }
        int par = par(arr, head, tail);
        sort(arr, head, par-1);
        sort(arr, par, par + 1);
    }

    public static int par(Integer[] arr, int head, int tail){
        return 0;
    }
}
