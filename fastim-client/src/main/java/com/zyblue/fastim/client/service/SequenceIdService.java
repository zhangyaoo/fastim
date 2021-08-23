package com.zyblue.fastim.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author will
 * @date 2021/7/19 15:45
 */
@Component
public class SequenceIdService {
    private final static Logger log = LoggerFactory.getLogger(SequenceIdService.class);

    /**
     * 基准时间 2021-7-28 14:31
     */
    private static final Long BASE_TIME = 1627453863L;

    private final static long TIME_ID_BITS = 29;

    private final static long COUNTER_ID_BITS = 2;

    /**
     * 上次时间
     */
    private long lastTime = -1L;

    private int counter;

    private final static int MAX_SEQUENCE = (1 << COUNTER_ID_BITS) - 1;

    private final static long MAX_VAL = (1 << TIME_ID_BITS) - 1;

    public synchronized int generateSequenceId(){
        long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        if(currentTime > lastTime){
            counter = 0;
        }else if(currentTime == lastTime){
            if (counter < MAX_SEQUENCE) {
                counter++;
            } else {
                // 用完当前序列号，重新等待
                currentTime = getNextSecond();
                counter = 0;
            }
        }else {
            throw new RuntimeException(String.format("lastTime %s is after reference time %s", lastTime, currentTime));
        }

        lastTime = currentTime;
        long now = currentTime - BASE_TIME;
        if(now >= MAX_VAL){
            throw new RuntimeException("generateSequenceId error: time exhausted");
        }
        log.info("now:{},count:{}",now,counter);
        return ((int)now) << COUNTER_ID_BITS | counter;
    }

    private  long getNextSecond(){
        long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        while (currentTime <= lastTime){
            currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        }
        return currentTime;
    }
}
