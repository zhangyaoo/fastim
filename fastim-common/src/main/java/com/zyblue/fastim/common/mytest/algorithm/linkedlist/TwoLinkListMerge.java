package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * 两个有序链表合并
 * Author : will 2019.10.29
 * Function : 两个有序链表合并为一个新的有序链表并返回
 * Example:
 *  输入：1->2->4, 1->3->4
 *  输出：1->1->2->3->4->4
 *
 * 思路：递归
 * 1、对特殊情况进行处理：Head1 为空则返回 Head2 ，Head2 为空则返回 Head1。
 * 2、比较两个链表的第一个结点，确定头节点
 * 3、头结点确定后，继续在剩下的结点中选出下一个结点去链接到第二步选出的结点后面
 * 4、重复2、3步骤
 */
public class TwoLinkListMerge {
    /*public static void main(String[] args) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        Node node4 = new Node();
        Node node5 = new Node();
        Node node6 = new Node();

        node1.setNext(node2);
        node1.setData(1);

        node2.setNext(node3);
        node2.setData(2);

        node3.setNext(null);
        node3.setData(4);


        node4.setNext(node5);
        node4.setData(1);

        node5.setNext(node6);
        node5.setData(3);

        node6.setNext(null);
        node6.setData(4);

        Node mergeNode = merge(node1, node4);
        System.out.println("first node:" + mergeNode.getData());

        while (true){
            if(mergeNode == null){
                break;
            }
            System.out.println("node:" + mergeNode.getData());
            mergeNode = mergeNode.getNext();
        }
    }*/

    public static Node merge(Node head1, Node head2){
        Node newNode;
        /*// 理论上不会这种条件
        if(head1 == null && head2 == null){
            return null;
        }*/
        // 边界
        if(head1 == null){
            return head2;
        }else if(head2 == null){
            return head1;
        }else {
            // 判断大小
            if(head1.getData() >= head2.getData()){
                // 新的链表头指向最小的head
                newNode = head2;
                // 为链表头下一个节点重复执行以上步骤
                newNode.setNext(merge(head1, head2.getNext()));
            }else {
                newNode = head1;
                newNode.setNext(merge(head1.getNext(), head2));
            }
        }
        System.out.println("newNode current:" + newNode.getData());
        return newNode;
    }

    public static class Node {
        private Integer data;

        private Node next;

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
