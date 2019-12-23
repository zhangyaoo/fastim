package com.zyblue.fastim.common.algorithm.tree;

/**
 * 有序数组转二叉搜索树
 */
public class ArrayToTree {
    public static class TreeNode {
        int val;
        private TreeNode left;
        private TreeNode right;
        private TreeNode(int x) {
          val = x;
        }
    }

    /*public static void main(String[] args) {
        int[] ints = new int[5];
        ints[0] = 0;
        ints[1] = 1;
        ints[2] = 2;
        ints[3] = 3;

        TreeNode tree = change(ints);
    }*/

    public static TreeNode change(int[] ints){
        return getTree(ints, 0 , ints.length - 1);
    }

    public static TreeNode getTree(int[] ints, int head, int tail){
        if(head<=tail){
            int centre = (head + tail)/2;
            TreeNode treeNode = new TreeNode(ints[centre]);
            treeNode.left = getTree(ints, head, centre-1);
            treeNode.right = getTree(ints, centre+1, tail);
        }
        return null;
    }
}
