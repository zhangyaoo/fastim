package com.zyblue.fastim.logic.queue;

import java.util.Set;

/**
 * 环形队列节点
 */
public class CycleNode {
    /**
     * 第几环
     */
    private int cycleNum;

    /**
     * 任务列表
     */
    private Set<Task> tasks;

    /**
     * 下个指针
     */
    private CycleNode next;

    public int getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum) {
        this.cycleNum = cycleNum;
    }

    public CycleNode getNext() {
        return next;
    }

    public void setNext(CycleNode next) {
        this.next = next;
    }
}
