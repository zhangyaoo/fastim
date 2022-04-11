package com.zyblue.fastim.common.mytest.algorithm.superior;

/**
 *  给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
 * 输出：7
 * 解释：因为路径 1→3→1→1→1 的总和最小。
 *
 */
public class MinimumPathSumGrid {

    public int minPathSum(int[][] grid) {
        // 设 dp 为大小 m×n 矩阵，其中 dp[i][j] 的值代表直到走到 (i,j)的最小路径和。
        // 状态转移方程为： dp[x][y] = min(dp[x-1][y], dp[x][y-1]) + grid[x][y]
        // 一种方法是增加额外空间，直接修改原来的数组即可



        return 0;
    }
}
