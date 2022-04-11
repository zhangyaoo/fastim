package com.zyblue.fastim.logic.msg.manager;


import com.zyblue.fastim.logic.constant.DelayTaskType;
import com.zyblue.fastim.logic.delayqueue.LoopQueue;
import com.zyblue.fastim.logic.delayqueue.Task;
import com.zyblue.fastim.logic.delayqueue.TaskStrategy;
import com.zyblue.fastim.logic.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DelayQueueManager {

    private final static Logger log = LoggerFactory.getLogger(DelayQueueManager.class);


    @Resource
    private LoopQueue loopQueue;

    /**
     * 执行任务
     */
    public void runTask(Task task){
        log.info("runTask task:{}", task);
        DelayTaskType type = DelayTaskType.getTypeByValue(task.getTaskType());
        if(type == null){
            log.info("type is null");
            return;
        }
        TaskStrategy taskStrategy = SpringContextUtil.getBean(type.getBeanName());
        boolean result = taskStrategy.executeTask(task.getMap());
        log.info("runTask result:{}", result);
    }

    /**
     * 新增任务，执行一次
     */
    public void addTask(Task task, long minute){
        log.info("addTask minute:{}, task:{}", minute, task);
        boolean result = loopQueue.computeAndPutTask(minute, task);
        log.info("addTask result:{}", result);
    }

    /**
     * 新增任务，执行多次次
     * 延迟策略：每次延迟都比上次延迟一倍时间
     * repeat: 重复次数
     */
    public void addTask(Task task, long minute, Integer repeat) {
        log.info("addTask minute:{}, repeat:{}, task:{}", minute, repeat, task);
        addTask(task, minute);

        if(repeat >= 1) {
            long minuteReal = minute;
            for(int i = 1;i <= repeat;i++){
                minuteReal = minuteReal << 1;
                boolean result = loopQueue.computeAndPutTask(minuteReal, task);
                log.info("addTask result:{}", result);
            }
        }

        //new CountDownLatch()
    }
}
