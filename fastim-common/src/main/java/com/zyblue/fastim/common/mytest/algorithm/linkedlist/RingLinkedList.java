package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * 链表是否有环
 * Author : BlueSky 2019.10.29
 * 思路：利用快慢指针
 */
public class RingLinkedList {

    public static Boolean hasRing(Node head){
        if(head == null || head.getNext() == null){
            return false;
        }

        boolean flag;

        Node slowPointer = head;
        Node fastPointer = head.getNext();

        while (true){
            // 如果快指针走的快，指针指向NULL，那么不是环形
            if(fastPointer == null){
                flag = false;
                break;
            }

            // 表示有环
            if(slowPointer.getData().equals(fastPointer.getData())){
                flag = true;
                break;
            }

            slowPointer = slowPointer.getNext();
            fastPointer = fastPointer.getNext().getNext();
        }

        return flag;
    }

    /*public static void main(String[] args) {
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

        // 无环
        //node5.setNext(null);
        // 有环
        node5.setNext(node1);
        node5.setData(5);
        System.out.println("start");
        Boolean res = hasRing(node1);
        System.out.println("res:" + res);
    }*/

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
