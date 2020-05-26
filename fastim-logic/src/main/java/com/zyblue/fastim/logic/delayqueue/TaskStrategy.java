package com.zyblue.fastim.logic.delayqueue;

import java.util.Map;

public interface TaskStrategy {
    /**
     * 执行
     */
    boolean executeTask(Map<String,Object> map);
}
