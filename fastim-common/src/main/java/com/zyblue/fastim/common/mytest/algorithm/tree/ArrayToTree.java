package com.zyblue.fastim.common.mytest.algorithm.tree;

import com.zyblue.fastim.common.mytest.algorithm.TreeNode;

/**
 * 有序数组转二叉搜索树
 */
public class ArrayToTree {

    public static void main(String[] args) {
        int[] ints = new int[6];
        ints[0] = 0;
        ints[1] = 1;
        ints[2] = 2;
        ints[3] = 3;
        ints[4] = 4;
        ints[5] = 5;

        TreeNode tree = change(ints);

        LayerTree.foreach(tree);
    }

    public static TreeNode change(int[] ints){
        return getTree(ints, 0 , ints.length - 1);
    }

    public static TreeNode getTree(int[] ints, int head, int tail){
        if(head<=tail){
            int centre = (head + tail)/2;
            TreeNode treeNode = new TreeNode();
            treeNode.setVal(ints[centre]);
            treeNode.setLeft(getTree(ints, head, centre-1));
            treeNode.setRight(getTree(ints, centre+1, tail));
            return treeNode;
        }
        return null;
    }
}
