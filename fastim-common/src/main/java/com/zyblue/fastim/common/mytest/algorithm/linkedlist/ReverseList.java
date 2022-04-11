package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * @author will
 * @date 2022/2/22 9:46
 */
public class ReverseList {

    /**
     * 1、我们定义两个指针，分别称之为 g(guard 守卫) 和 p(point)。
     * 我们首先根据方法的参数 m 确定 g 和 p 的位置。将 g 移动到第一个要反转的节点的前面，将 p 移动到第一个要反转的节点的位置上。我们以 m=2，n=4为例。
     * 2、将 p 后面的元素删除，然后添加到 g 的后面。也即头插法。
     * 3、根据 m 和 n 重复步骤（2）
     * 4、返回 dummyHead.next
     */
    public Node reverseBetweenV2(Node head, int left, int right) {
        // 定义一个dummyHead, 方便处理
        Node dummyHead = new Node();
        dummyHead.next = head;

        // 初始化指针
        Node g = dummyHead;
        Node p = dummyHead.next;

        // 将指针移到相应的位置
        for(int step = 0; step < left - 1; step++) {
            g = g.next; p = p.next;
        }

        // 头插法插入节点
        for (int i = 0; i < right - left; i++) {
            Node removed = p.next;
            p.next = p.next.next;

            // g.next改成p
            removed.next = g.next;
            g.next = removed;
        }

        return dummyHead.next;
    }


    public Node reverseBetween(Node head, int left, int right) {
        Node dummyHead = new Node();
        dummyHead.setNext(head);

        Node pre = dummyHead;
        Node first = dummyHead.next;
        int i = 0;
        while (i < left){
            pre = first;
            first = first.next;
            i++;
        }

        Node preTemp = null;
        Node tail = first;
        Node nextNode = first.next;
        int j = 0;
        while (j < right - left){
            first.setNext(preTemp);
            preTemp = first;
            first = nextNode;
            nextNode = first.next;
            j++;
        }

        pre.setNext(first);
        tail.setNext(nextNode);

        return dummyHead.getNext();
    }
}
