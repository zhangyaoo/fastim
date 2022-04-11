package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * @author will
 * @date 2022/2/9 11:57
 *
 * 顺时针打印矩阵
 */
public class SpiralOrder {

    public int[] spiralOrder(int[][] matrix){


        if(matrix == null ||matrix.length == 0){
            return new int[0];
        }

        int left = 0, up = 0;
        int right = matrix[0].length - 1;
        int down = matrix.length - 1;
        int[] res = new int[(right + 1) * (down + 1)];
        int index = 0;

        while(up <= down && left <= right) {
            for(int i = left; i <= right; i++) {
                res[index++] = matrix[up][i];
            }
            up++;

            for(int i = up; i <= down; i++) {
                res[index++] = matrix[i][right];
            }
            right--;

            for(int i = right; i >= left && up <= down; i--) {
                res[index++] = matrix[down][i];
            }
            down--;

            for(int i = down; i >= up && left <= right; i--){
                res[index++] = matrix[i][left];
            }
            left++;
        }

        return res;
    }
}
