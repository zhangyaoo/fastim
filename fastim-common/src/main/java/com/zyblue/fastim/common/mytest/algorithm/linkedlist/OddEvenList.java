package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * @author will
 * @date 2021/12/13 14:48
 *
 * 奇偶列表:给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 1->3->5->2->4->NULL
 *
 * 输入: 2->1->3->5->6->4->7->NULL
 * 输出: 2->3->6->7->1->5->4->NULL
 */
public class OddEvenList {
    public Node oddEvenList(Node head) {
        Node oddHead = head;
        Node evenHead = head.getNext();

        Node oddTail = head;
        Node evenTail = head.getNext();
        Node p = head.getNext().getNext();
        while (p != null && p.getNext() != null){
            oddTail.setNext(p);
            oddTail = p;
            // 向后进一位
            p = p.getNext();
            if(p != null){
                evenTail.setNext(p);
                evenTail = p;
                // 向后进一位
                p = p.getNext();
            }
        }

        oddTail.setNext(evenHead);

        return oddHead;
    }
}
