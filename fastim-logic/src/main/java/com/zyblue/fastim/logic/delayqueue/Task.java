package com.zyblue.fastim.logic.delayqueue;


import java.io.Serializable;
import java.util.Map;

public class Task implements Serializable {

    private static final long serialVersionUID = -6223581184997995115L;

    private Byte taskType;

    private Map<String,Object> map;

    public Byte getTaskType() {
        return taskType;
    }

    public void setTaskType(Byte taskType) {
        this.taskType = taskType;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
