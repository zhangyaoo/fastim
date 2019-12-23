package com.zyblue.fastim.common.algorithm.tree;


import com.zyblue.fastim.common.algorithm.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 树的左视图
 * @Author : BlueSky 2019.12.23
 *        4
 *     2     6
 *   1  3  5  7
 * 0            8
 *
 * -----------> 4,2,1,0
 *
 *        4
 *     2     6
 *   1  3  5  7
 *              8
 *
 * -----------> 4,2,1,8
 */
public class LeftViewTree {

    public void foreach(TreeNode node){
        List<TreeNode> linkedList = new LinkedList();
        linkedList.add(node);
        while (!linkedList.isEmpty()){
            if(node.getLeft() != null){

            }
            if(node.getRight() != null){

            }
        }
    }
}
