package com.zyblue.fastim.common.mytest.algorithm.tree;

import com.zyblue.fastim.common.mytest.algorithm.TreeNode;

import java.util.LinkedList;

/**
 * 层序遍历二叉树
 * Author : will 2019.11.15
 * 思路：利用队列实现
 */
public class LayerTree {

    public static void foreach(TreeNode node){

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
}
