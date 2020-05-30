package com.zyblue.fastim.common.algorithm.other;

/**
 * 场景：查找输入中重复出现超过一半以上(n/2)的元素 ,Θ(n)和Θ(1)的时间和空间复杂度
 * 找出数组中元素最多的那个数字，长度为n，这个数字出现次数>n/2
 * 思路1：利用hash表 Θ(n)和Θ(n)的时间和空间复杂度
 * 思路2：排序，然后取中间值，nΘ(logn)时间复杂度
 * 思路3：摩尔投票法 Θ(n)和Θ(1)的时间和空间复杂度：
 *  摩尔投票：遍历，每次将两个不相同的数删除，最终剩下的就是要找的数
 */
public class FindArrMoreNum {

    public static int findV1(int[] arr){
        int res = 0;
        int count = 0;

        for(int i=0; i<arr.length; i++){
            if(count == 0){
                res = arr[i];
                count++;
            }else if(count > 0){
                if(res == arr[i]){
                    count++;
                }else {
                    count--;
                }
            }
        }
        return res;
    }

    public static int find(int[] arr){

        int res = 0;
        int count = 0;

        for(int i=0; i<arr.length; i++){
            if(count == 0){
                res = arr[i];
                count = 1;
            }else if(res != arr[i]){
                count--;
            }else {
                count++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] ints = new int[15];
        ints[0] = 5;
        ints[1] = 3;
        ints[2] = 2;
        ints[3] = 2;
        ints[4] = 3;
        ints[5] = 5;
        ints[6] = 5;
        ints[7] = 6;
        ints[8] = 6;
        ints[9] = 6;
        ints[10] = 6;
        ints[11] = 6;
        ints[12] = 6;
        ints[13] = 6;

        int i = find(ints);
        System.out.println("i:" + i);
    }
}
