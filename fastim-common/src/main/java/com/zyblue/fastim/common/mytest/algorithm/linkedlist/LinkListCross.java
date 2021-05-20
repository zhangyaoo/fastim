package com.zyblue.fastim.common.mytest.algorithm.linkedlist;

/**
 * @author will
 * @date 2020/11/11 10:08
 *
 * 两条链表想相交点
 */
public class LinkListCross {


    private static class ListNode{
        public ListNode next;
    }

    /*public static void main(String[] args) {

    }*/

    public static int Length(ListNode list) {  //求两个链表的长度
        int count = 0;
        while (list != null) {
            list = list.next;
            count++;
        }
        return count;

    }

    public static ListNode Point(ListNode list1, ListNode list2) {
        int len1 = Length(list1);
        int len2 = Length(list2);
        ListNode longL = list1;
        ListNode shortL = list2;
        int diff = len1 - len2;
        if (len2 > len1) {
            longL = list2;
            shortL = list1;
            diff = len2 - len1;
        }
        // 先走diff步
        while (diff-- != 0) {
            longL = longL.next;
        }
        // 判断相交
        while (true) {
            if (longL == shortL) {
                return longL;
            }
            longL = longL.next;
            shortL = shortL.next;
        }
    }
}