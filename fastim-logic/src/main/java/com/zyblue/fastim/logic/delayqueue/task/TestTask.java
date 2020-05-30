package com.zyblue.fastim.logic.delayqueue.task;


import com.zyblue.fastim.logic.delayqueue.TaskStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("testTask")
public class TestTask implements TaskStrategy {

    private final static Logger log = LoggerFactory.getLogger(TestTask.class);

    @Override
    public boolean executeTask(Map<String,Object> map) {
        log.info("map:{}", map);
        return true;
    }
}
