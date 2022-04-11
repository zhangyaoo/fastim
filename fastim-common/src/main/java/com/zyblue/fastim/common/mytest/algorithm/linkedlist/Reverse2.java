package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * @author will
 * @date 2022/2/24 12:26
 */
public class Reverse2 {

    public Node swapPairs(Node head) {
        Node  node = new Node();
        node.setNext(head);

        Node pre = node;
        Node cur = head;
        while (cur != null && cur.next != null){
            Node next = cur.next;
            next.next = cur;
            pre.next = cur.next;
            cur.next = cur.next.next;
            pre = cur;
            cur = cur.next;
        }
        return node.next;
    }

    public Node aa(Node head){
        Node dummyHead = new Node();
        dummyHead.next = head;
        Node temp = dummyHead;
        while (temp.next != null && temp.next.next != null) {
            Node node1 = temp.next;
            Node node2 = temp.next.next;
            temp.next = node2;
            node1.next = node2.next;
            node2.next = node1;
            temp = node1;
        }
        return dummyHead.next;
    }
}
