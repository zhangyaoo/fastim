package com.zyblue.fastim.common.mytest.algorithm.other;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 顺时针打印矩阵
 *
 * 思路：
 * 1、先打印最外层：定义对端的两个坐标，有了两个坐标就可以打印四边的点
 * 2、然后变换坐标，x-1和y-1，打印倒数第二层
 * 3、拼接所有的点打印
 */
public class PrintMatrixInCircle {

    private static List<Integer> LIST =  Lists.newArrayList();

    public static void main(String[] args) {

        int[][] matrix = {
            {1,2,3,4,5},
            {6,7,8,9,10},
            {11,12,13,14,15},
            {16,17,18,19,20},
            {21,22,23,24,25}
        };

        printMatrixCycle(matrix);
    }

    private static void printMatrixCycle(int[][] matrix){
        int lengthY = matrix.length;
        int lengthX = matrix[0].length;

        // 左上角坐标
        int x1 = 0;
        int y1 = 0;

        // 右下角坐标
        int x2 = lengthX - 1;
        int y2 = lengthY - 1;

        // 每一次循环最外层
        while (x1 <= x2 && y1 <= y2){
            print(matrix, x1,y1,x2,y2);
            x1++;y1++;x2--;y2--;
        }
        System.out.println(LIST);
    }

    private static void print(int[][] matrix , int x1,int y1,int x2,int y2){
        // 临界值处理
        if(x1 == x2){
            for(int i = y1; i <= y2; i++){
                LIST.add(matrix[i][x1]);
            }
        }else if(y1 == y2){
            for(int i = x1; i <= x2; i++){
                LIST.add(matrix[y1][i]);
            }
        }else {
            int currentX = x1;
            int currentY = y1;
            // 循环这一次的四边，注意每一边的循环终止条件(避免重复打印某一个点)
            while (currentX < x2){
                LIST.add(matrix[y1][currentX]);
                currentX++;
            }
            while (currentY < y2){
                LIST.add(matrix[currentY][x2]);
                currentY++;
            }
            while (currentX > x1){
                LIST.add(matrix[y2][currentX]);
                currentX--;
            }
            while (currentY > y1){
                LIST.add(matrix[currentY][x1]);
                currentY--;
            }
        }
    }
}
