package com.zyblue.fastim.common.algorithm.linkedlist;

/**
 * 判断是否是回文链表
 * Author : BlueSky 2019.12.24
 * 要求：O(n) 时间复杂度和 O(1) 空间复杂度
 *
 * 输入: 1->2->2->1
 * 输出: true
 *
 * 输入: 1->2
 * 输出: false
 *
 * 如果没有O(1)空间复杂度这个条件  可以利用栈来解决
 *
 * 思路：  1 -> 2 -> 3 -> 2 -> 1  改为  1 -> 2 -> 3 <- 2 <- 1
 * 1、快慢指针，慢指针后面进行反转
 * 2、首尾指针开始遍历链表
 * 3、判断是否相等
 */
public class SymmetryList {

}
