package com.zyblue.fastim.common.algorithm.tree;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
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

    public static void foreach(TreeNode treeNode){

        LinkedList<TreeNode> linkedList = new LinkedList<TreeNode>();
        linkedList.add(treeNode);
        List<Integer> res = Lists.newArrayList();
        while (!linkedList.isEmpty()){

            // 获取这一层所有大小
            int size = linkedList.size();
            System.out.println("size:"+size);

            // 第一个节点就是有效节点
            res.add(linkedList.peekFirst().getVal());

            // 遍历这一层节点
            for(int i = 0;i<size;i++){
                TreeNode node = linkedList.poll();
                if(node.getLeft() != null){
                    linkedList.add(node.getLeft());
                }
                if(node.getRight() != null){
                    linkedList.add(node.getRight());
                }
            }
        }

        System.out.println("res:"+ JSONObject.toJSONString(res));
    }
}
