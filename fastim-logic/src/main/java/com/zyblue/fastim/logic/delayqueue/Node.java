package com.zyblue.fastim.logic.delayqueue;


import java.io.Serializable;

public class Node implements Serializable {

    private static final long serialVersionUID = 1342764471428569486L;

    private long cycleNum;

    private Task task;

    public long getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(long cycleNum) {
        this.cycleNum = cycleNum;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Node{" +
                "cycleNum=" + cycleNum +
                ", task=" + task +
                '}';
    }
}
