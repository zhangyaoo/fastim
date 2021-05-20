package com.zyblue.fastim.common.mytest.algorithm.tree;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 非递归方式中序遍历二叉树
 * Author : BlueSky 2019.12.09
 */
public class ForeachTreeUseNoRecursion {


    /**
     * 非递归方式遍历二叉树
     */
    public static void noRecursion(TreeNode root) {
        if(root == null){
            return;
        }

        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode curNode = root;

        // 中序遍历打印结果
        ArrayList<Object> zhongxuNumList = Lists.newArrayList();

        while (curNode != null || !stack.empty()){
            while (curNode != null){
                // 前序遍历打印结果
                // zhongxuNumList.add(node.val);
                stack.push(curNode);
                curNode = curNode.getLeft();
            }

            if(!stack.empty()){
                TreeNode node = stack.pop();
                zhongxuNumList.add(node.val);
                curNode = node.getRight();
            }
        }
    }





   /* public static void main(String[] args) {

    }*/

    /**
     * 递归方式遍历二叉树
     */
    public static void recursion(TreeNode node){
        if(node == null){
            return;
        }
        recursion(node.getLeft());
        System.out.println("node val:" + node.getVal());
        recursion(node.getRight());
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
