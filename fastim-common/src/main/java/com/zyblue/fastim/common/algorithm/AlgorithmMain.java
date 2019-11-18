package com.zyblue.fastim.common.algorithm;

import java.util.HashMap;

public class AlgorithmMain {
    public static void main(String[] args) {
        LayerTree.TreeNode treeNode7 = new LayerTree.TreeNode();
        treeNode7.setVal(7);
        LayerTree.TreeNode treeNode6 = new LayerTree.TreeNode();
        treeNode6.setVal(6);
        LayerTree.TreeNode treeNode5 = new LayerTree.TreeNode();
        treeNode5.setVal(5);
        LayerTree.TreeNode treeNode4 = new LayerTree.TreeNode();
        treeNode4.setVal(4);
        LayerTree.TreeNode treeNode3 = new LayerTree.TreeNode();
        treeNode3.setVal(3);
        LayerTree.TreeNode treeNode2 = new LayerTree.TreeNode();
        treeNode2.setVal(2);
        LayerTree.TreeNode treeNode1 = new LayerTree.TreeNode();
        treeNode1.setVal(1);

        treeNode4.setLeft(treeNode2);
        treeNode2.setLeft(treeNode1);
        treeNode2.setRight(treeNode3);
        treeNode4.setRight(treeNode6);
        treeNode6.setLeft(treeNode5);
        treeNode6.setRight(treeNode7);

        LayerTree layerTree = new LayerTree();
        layerTree.foreach(treeNode4);
    }
}
