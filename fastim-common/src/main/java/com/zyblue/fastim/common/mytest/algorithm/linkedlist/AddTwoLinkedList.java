package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * @author will
 * @date 2021/12/28 15:30
 *
 * 两个链表相加
 * 2->3->4->5
 * 2->3->6->4
 * res： 4->6->0->0->1
 *
 * 小技巧：对于链表问题，返回结果为头结点时，通常需要先初始化一个预先指针 pre，该指针的下一个节点指向真正的头结点head。
 * 使用预先指针的目的在于链表初始化时无可用节点值，而且链表构造过程需要指针移动，进而会导致头指针丢失，无法返回结果。
 */
public class AddTwoLinkedList {
    public Node addTwoNumbers(Node l1, Node l2) {
        // 头节点
        Node pre = new Node();
        pre.data = 0;
        // 进位
        int carry = 0;
        // 指针
        Node cur = pre;
        while (l1 != null && l2 != null){
            // 每次计算要计算进位
            int sum = l1.data + l2.data + carry;
            carry = sum / 10;
            Node next = new Node();
            next.data = sum % 10;
            // 构建新节点，建立连接
            cur.setNext(next);
            cur = cur.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        // 最后一次进位
        if(carry > 0){
            Node next = new Node();
            next.data = carry % 10;
            cur.setNext(next);
        }
        return pre.getNext();
    }
}
