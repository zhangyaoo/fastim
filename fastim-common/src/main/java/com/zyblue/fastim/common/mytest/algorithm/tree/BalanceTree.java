package com.zyblue.fastim.common.mytest.algorithm.tree;

/**
 * 判断是否是平衡二叉树
 * Author : BlueSky 2019.11.14
 * 平衡二叉树定义：左子树和右子树的高度相差值小于1
 * 思路一：
 * 1、遍历每个结点，借助一个获取树深度的递归函数
 * 2、根据该结点的左右子树高度差判断是否平衡
 * 缺点:从上往下遍历，时间复杂度高，会重复计算高度
 *
 * 思路二：
 * 1、后序遍历，遍历左子树和右子树
 * 2、递归的方法，求左子树和右子树的高度
 * 3、每次在求出左子树和右子树的高度的时候比较一下两者之间的差值是否大于1
 */
public class BalanceTree {

    //全局变量
    private boolean isBalanceTree = true;

    public Boolean isBalanceTree(TreeNode node){
        if(node == null){
            return true;
        }

        balanceTreeLength(node);

        return isBalanceTree;
    }

    public int balanceTreeLength(TreeNode node){
        if(node == null){
            return 0;
        }

        // 左子树到叶子节点的高度
        int lengthL = balanceTreeLength(node.getLeft());
        // 右子树到叶子节点的高度
        int lengthR = balanceTreeLength(node.getRight());
        if(Math.abs(lengthL - lengthR) >= 1){
            isBalanceTree = false;
        }

        // 树的高度计算通式
        return Math.max(lengthL, lengthR) + 1;
        //return lengthL > lengthR ? lengthL + 1:lengthR + 1;
    }

    public static class TreeNode {
        private int val;

        private TreeNode left;

        private TreeNode right;

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }
}
