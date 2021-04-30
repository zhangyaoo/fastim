package com.zyblue.fastim.common.mytest.algorithm.tree;

/**
 * 判断是否是对称二叉树
 * Author : BlueSky 2019.11.14
 * 例如
 *    1
 *   / \
 *  2   2
 * / \ / \
 * 3 4 4  3
 * 思路：
 * 1、递归实现
 * 2、根节点相等，左子树的左子树和右子树的右子树对称，左子树的右子树和右子树的左子树对称
 */
public class SymmetryTree {

    public Boolean isSymmetryTree(TreeNode node){
        if(node == null){
            return true;
        }else {
            return isSymmetryTree(node.getLeft(), node.getRight());
        }
    }

    public Boolean isSymmetryTree(TreeNode nodeLeft, TreeNode nodeRight) {
        if(nodeLeft == null && nodeRight == null){
            return true;
        }else if(nodeLeft == null || nodeRight == null){
            return false;
        }else {
            if(nodeLeft.getVal() != nodeRight.getVal()){
                return false;
            }

            // 递归每一个子树
            return isSymmetryTree(nodeLeft.getLeft(), nodeRight.getRight())
                    && isSymmetryTree(nodeLeft.getRight(), nodeRight.getLeft());
        }
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
