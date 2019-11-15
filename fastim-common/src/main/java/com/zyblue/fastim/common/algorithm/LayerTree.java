package com.zyblue.fastim.common.algorithm;

import java.util.LinkedList;

/**
 * 层序遍历数组
 * Author : BlueSky 2019.11.15
 * 思路：利用队列实现
 */
public class LayerTree {

    public void foreach(TreeNode node){

        LinkedList<TreeNode> linkedList = new LinkedList();
        linkedList.add(node);
        while (true){
            if(linkedList.isEmpty()){
                break;
            }
            TreeNode poll = linkedList.poll();
            System.out.println(poll.getVal());
            if(poll.getLeft() != null){
                linkedList.add(poll.getLeft());
            }
            if(poll.getRight() != null){
                linkedList.add(poll.getRight());
            }
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
