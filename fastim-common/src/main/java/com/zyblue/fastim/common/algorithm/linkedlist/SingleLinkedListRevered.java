package com.zyblue.fastim.common.algorithm.linkedlist;

/**
 * 单链表反转
 * Author : BlueSky 2019.10.29
 * Function：把每个节点的指针域由原来的指向下一个节点变为指向其前一个节点
 * Example:
 *  输入: 1->2->3->4->5->NULL
 *  输出: 5->4->3->2->1->NULL
 */
public class SingleLinkedListRevered {

    public static Node reversed1(Node head) {
        if(head == null || head.getNext() == null){
            return null;
        }

        Node pre = null;
        Node cur = head;
        while (cur != null){
            Node next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }
        return pre;
    }



    public static void main(String[] args) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        Node node4 = new Node();
        Node node5 = new Node();

        node1.setNext(node2);
        node1.setData(1);

        node2.setNext(node3);
        node2.setData(2);

        node3.setNext(node4);
        node3.setData(3);

        node4.setNext(node5);
        node4.setData(4);

        node5.setNext(null);
        node5.setData(5);

        Node reversed = reversed1(node1);
        System.out.println("node:" + reversed.getData());
        System.out.println("node next:" + reversed.getNext().getData());
        System.out.println("node next next:" + reversed.getNext().getNext().getData());

    }

    public static Node reversed(Node head) {
        // 头为空返回空
        if(head == null || head.getNext() == null){
            return null;
        }

        // 头一个指针为空
        Node preNode = null;
        // 当前指针为head
        Node curNode = head;
        // 先设置为null
        Node nextNode = null;

        while (true){
            if(curNode == null){
                break;
            }

            // 指定下一个node
            nextNode = curNode.getNext();
            // 当前指针指向前一个指针
            curNode.setNext(preNode);
            // 前一个指针和当前指针向后移动
            preNode = curNode;
            curNode = nextNode;
        }
        return preNode;
    }

    public static class Node {
        private Object data;

        private Node next;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
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