package com.zyblue.fastim.leaf.manager;

import com.zyblue.fastim.leaf.config.ZKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 雪花算法SnowFlake
 * @author blueSky
 */
@Component
public class SnowFlakeManager {

    private final static Logger logger = LoggerFactory.getLogger(SnowFlakeManager.class);

    private final static long WORKER_ID_SHIFT = 12L;

    private final static long WORKER_ID_BITS = 10L;

    private final static long TIMESTAMP_LEFT_SHIFT = 12L + WORKER_ID_BITS;

    private static final long START_TIME_STAMP = 1525229976179L;

    private static final short MAX_SEQUENCE = 4095;

    /**
     * 上次时间
     */
    private long lastTimestamp = -1L;

    private long  counter = 0L;

    @Resource
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
                // 用完当前序列号，重新等待下一毫秒
                try {
                    wait(1);
                }catch (Exception e){
                    logger.error("wait interrupted");
                    throw new RuntimeException("wait interrupted ");
                }
                timestamp = System.currentTimeMillis();
                counter = 0L;
            }
        } else {
            //如果是新的ms开始
            counter = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIME_STAMP) << TIMESTAMP_LEFT_SHIFT) | workerId << WORKER_ID_SHIFT | counter;
    }
}
