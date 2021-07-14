package com.zyblue.fastim.leaf.manager;

import com.zyblue.fastim.common.constant.CommonConstant;
import com.zyblue.fastim.leaf.config.ZKConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 雪花算法
 * @author will
 */
@Component
public class SnowFlakeManager {

    private final static long WORKER_ID_SHIFT = 12L;

    private final static long WORKER_ID_BITS = 10L;

    private final static long TIMESTAMP_LEFT_SHIFT = WORKER_ID_SHIFT + WORKER_ID_BITS;

    /**
     * 2020-10-01 00:00:00
     */
    private static final long START_TIME_STAMP = 1601481600000L;

    private static final short MAX_SEQUENCE = 63;

    /**
     * 上次时间
     */
    private long lastTimestamp = -1L;

    /**
     * 计数器
     */
    private long  counter = 0L;

    /**
     * 机器ID
     */
    private Long hostId;

    @Resource
    private ZKConfig zkConfig;

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取本机的机器ID
     */
    @PostConstruct
    private void initHost(){
        if(zkConfig != null){
            hostId = Long.valueOf(zkConfig.getWorkerId());
        } else {
            hostId = redisTemplate.opsForValue().increment(CommonConstant.Redis.IM_LEAF_WORKER_ID_PREFIX, 1L);
        }
    }

    public synchronized Long nextLong(){
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                // 重新等待
                timestamp = getNextMillSecond();
            } else {
                throw new RuntimeException(String.format("lastTimestamp %s is after reference time %s", lastTimestamp, timestamp));
            }
        }
        if (lastTimestamp == timestamp) {
            if (counter < MAX_SEQUENCE) {
                counter++;
            } else {
                // 用完当前序列号，重新等待
                timestamp = getNextMillSecond();
                counter = 0L;
            }
        } else {
            counter = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - START_TIME_STAMP) << TIMESTAMP_LEFT_SHIFT) | hostId << WORKER_ID_SHIFT | counter;
    }

    private  long getNextMillSecond(){
        long currentTimeMillis = System.currentTimeMillis();
        while (currentTimeMillis <= lastTimestamp){
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis;
    }
}
