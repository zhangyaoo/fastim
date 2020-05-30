package com.zyblue.fastim.common.algorithm;

import com.zyblue.fastim.common.algorithm.tree.ForeachTreeUseNoRecursion;
import com.zyblue.fastim.common.algorithm.tree.LayerTree;
import com.zyblue.fastim.common.algorithm.tree.LayerTreeV2;
import com.zyblue.fastim.common.algorithm.tree.LeftViewTree;

import java.util.concurrent.Executors;

public class AlgorithmMain {
    public static void main(String[] args) {
        TreeNode treeNode7 = new TreeNode();
        treeNode7.setVal(7);
        TreeNode treeNode8 = new TreeNode();
        treeNode8.setVal(8);
        TreeNode treeNode6 = new TreeNode();
        treeNode6.setVal(6);
        TreeNode treeNode5 = new TreeNode();
        treeNode5.setVal(5);
        TreeNode treeNode4 = new TreeNode();
        treeNode4.setVal(4);
        TreeNode treeNode3 = new TreeNode();
        treeNode3.setVal(3);
        TreeNode treeNode2 = new TreeNode();
        treeNode2.setVal(2);
        TreeNode treeNode1 = new TreeNode();
        treeNode1.setVal(1);
        TreeNode treeNode0 = new TreeNode();
        treeNode0.setVal(0);
        treeNode1.setLeft(treeNode0);
        treeNode4.setLeft(treeNode2);
        treeNode2.setLeft(treeNode1);
        treeNode2.setRight(treeNode3);
        treeNode4.setRight(treeNode6);
        treeNode6.setLeft(treeNode5);
        treeNode6.setRight(treeNode7);
        treeNode7.setRight(treeNode8);
        /**
         *        4
         *     2     6
         *   1  3  5  7
         * 0            8
         */



        //LayerTree layerTree = new LayerTree();
        //layerTree.foreach(treeNode4);

        //LayerTreeV2 layerTreeV2 = new LayerTreeV2();
        //layerTreeV2.foreachV1(treeNode4);

        //LeftViewTree leftViewTree = new LeftViewTree();
        //leftViewTree.foreach(treeNode4);


       ForeachTreeUseNoRecursion.noRecursion(treeNode4);

        /*MultiThread multiThread = new MultiThread();
        multiThread.test1();
        multiThread.test2();*/
    }
}
