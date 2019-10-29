package com.zyblue.fastim.common.algorithm;

/**
 * 删除链表的倒数第 N 个节点
 * Example：
 *  给定一个链表: 1->2->3->4->5, 和 n = 2.
 *  当删除了倒数第二个节点后，链表变为 1->2->3->5.
 *
 * 思路1：一趟扫描获得链表长度l，然后删除从前往后的第l - n +1个节点。
 *
 * 思考进阶：如何用一遍遍历来实现？
 * 思路2：利用双指针，间隔n，双指针循环，当后面一个指针指向NULL时候，前一个指针指向的位置就是需要删除节点的上一个节点，然后删除前一个指针的下一个节点。
 */
public class DeleteOneLinkedList {

    public static Node deleteOneLinkedList(Integer n, Node head){
        if(n < 1 || head == null || head.getNext() == null){
            return null;
        }

        Node frontNode = head;
        Node behindNode = head.getNext();
        for(int i = 0; i<= n-1; i++){
            behindNode = behindNode.getNext();
        }

        System.out.println("init frontNode:" + frontNode.getData());
        System.out.println("init behindNode:" + behindNode);

        while (true){
            // 后面指针就指向NULL的话，就删除
            if(behindNode == null){
                // 修改头节点指针来删除
                frontNode.setNext(frontNode.getNext().getNext());
                break;
            }
            System.out.println("frontNode:" + frontNode);
            System.out.println("behindNode:" + behindNode);
            // 指针同时向后移动
            frontNode = frontNode.getNext();
            behindNode = behindNode.getNext();
        }
        return head;
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

        Integer n = 4;
        System.out.println("start");
        Node nodeHead = deleteOneLinkedList(n, node1);

        // 打印
        while (nodeHead != null){
            System.out.println("node:" + nodeHead.getData());
            nodeHead = nodeHead.getNext();
        }
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
