package com.zyblue.fastim.common.algorithm.tree;

import com.zyblue.fastim.common.algorithm.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 层序遍历数组,锯齿形遍历
 * 第二层遍历顶点
 * 第二层按照右向左遍历
 * 第三层按照左向右遍历
 * 第四层按照右向左遍历
 * 依次。。。。
 * Author : BlueSky 2019.11.15
 *    4
 *  2   6
 * 1 3 5 7
 * 输出：4  6  2  1 3 5 7
 */
public class LayerTreeV2 {

    public  void foreachV1(TreeNode treeNode){
        if(treeNode == null){
            return;
        }

        List<List<Integer>> list = new ArrayList<List<Integer>>();

        Stack<TreeNode> stackLeftFirst = new Stack<TreeNode>();
        Stack<TreeNode> stackRightFirst = new Stack<TreeNode>();
        stackLeftFirst.push(treeNode);
        //int high = 2;
        while (!stackLeftFirst.empty() || !stackRightFirst.empty()){

            List<Integer> arrayList1 = new ArrayList();
            while(!stackLeftFirst.empty()){
                TreeNode node = stackLeftFirst.pop();
                System.out.println(node.getVal());
                arrayList1.add(node.getVal());
                if(node.getLeft() != null){
                    stackRightFirst.push(node.getLeft());
                }
                if(node.getRight() != null){
                    stackRightFirst.push(node.getRight());
                }
            }
            list.add(arrayList1);

            List<Integer> arrayList2 = new ArrayList();
            while(!stackRightFirst.empty()){
                TreeNode node = stackRightFirst.pop();
                System.out.println(node.getVal());
                arrayList2.add(node.getVal());
                if(node.getRight() != null){
                    stackLeftFirst.push(node.getRight());
                }
                if(node.getLeft() != null){
                    stackLeftFirst.push(node.getLeft());
                }
            }
            list.add(arrayList2);
        }
        System.out.println(list.toString());
    }
}
