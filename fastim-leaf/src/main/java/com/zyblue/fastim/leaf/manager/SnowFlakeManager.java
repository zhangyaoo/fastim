package com.zyblue.fastim.leaf.manager;

import com.zyblue.fastim.leaf.config.ZKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 雪花算法SnowFlake
 * @Author blueSky
 */
@Component
public class SnowFlakeManager {

    private final static Logger logger = LoggerFactory.getLogger(SnowFlakeManager.class);

    private final long workerIdShift = 12L;

    private final long workerIdBits = 10L;

    private final long timestampLeftShift = 12L + workerIdBits;

    private static final long START_TIME_STAMP = 1525229976179L;

    private static final Random RANDOM = new Random();

    private static final short MAX_SEQUENCE = 4095;

    /**
     * 上次时间
     */
    private long lastTimestamp = -1L;

    private long  counter = 0L;

    @Autowired
    private ZKConfig zKConfig;

    public synchronized Long nextLong(){
        Integer workerId = zKConfig.getWorkerId();
        logger.info("workerId:{}", zKConfig.getWorkerId());

        if(workerId == null){
            return null;
        }
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = System.currentTimeMillis();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("lastTimestamp %s is after reference time %s", lastTimestamp, timestamp));
                    }
                } catch (InterruptedException e) {
                    logger.error("wait interrupted");
                    throw new RuntimeException(String.format("lastTimestamp %s is after reference time %s", lastTimestamp, timestamp));
                }
            } else {
                throw new RuntimeException(String.format("lastTimestamp %s is after reference time %s", lastTimestamp, timestamp));
            }
        }
        if (lastTimestamp == timestamp) {
            if (this.counter < MAX_SEQUENCE) {
                this.counter++;
            } else {
                throw new RuntimeException("Sequence exhausted at " + this.counter);
            }
        } else {
            //如果是新的ms开始
            counter = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIME_STAMP) << timestampLeftShift) | workerId << workerIdShift | counter;
    }
}
