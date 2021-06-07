package com.zyblue.fastim.leaf;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mingyuanyun.cache.client.CacheClient;
import com.mingyuanyun.lock.client.LockClient;
import com.zyblue.fastim.leaf.manager.SnowFlakeManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = FastimLeafApplication.class)
public class CommonTest {
    private final static Logger logger = LoggerFactory.getLogger(CommonTest.class);

    @Autowired
    private SnowFlakeManager snowFlakeManager;

    @Autowired
    private LockClient distributeLockClient;

    @Resource
    private CacheClient<String> cacheClient;

    @Test
    public void testCache(){
        cacheClient.set("zhang222", "a");
        String zhang222 = cacheClient.get("zhang222");
        cacheClient.expire("zhang222", 10L ,TimeUnit.SECONDS);
        logger.info("zhang222:{}", zhang222);
    }

    @Test
    public void testLock(){
        boolean result1 = distributeLockClient.lock("zhangy");
        boolean result2 = distributeLockClient.lock("zhangy");
        distributeLockClient.unlock("zhangy");
        boolean result3 = distributeLockClient.hasLock("zhangy");
        distributeLockClient.unlock("zhangy");
        boolean result4 = distributeLockClient.hasLock("zhangy");
        logger.info("result1:{}, result2:{}, result3:{}, result4:{}", result1, result2, result3, result4);


        distributeLockClient.batchLock(Lists.newArrayList("oldDriverA", "oldDriverB"));
        distributeLockClient.batchUnlock(Lists.newArrayList("oldDriverA", "oldDriverB"));

        try {
            distributeLockClient.lockReleaseTime("oldDriverA", 10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error:", e);
        }

        try {
            distributeLockClient.lockWaitTime("oldDriverA", 10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error:", e);
        }

        try {
            boolean oldDriverA = distributeLockClient.lockWaitReleaseTime("oldDriverA", 10L, 10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("error:", e);
        }

        boolean oldDriverA = distributeLockClient.hasLock("oldDriverA");

    }

    /*@Test
    public void testLock2() throws InterruptedException {
        RLock a = redissonClient.getLock("a");
        RLock b = redissonClient.getLock("b");


        RLock zhangy = redissonClient.getLock("zhangy");
        zhangy.lock();
        new Thread(() ->{
            RLock zhangy1 = redissonClient.getLock("zhangy");
            zhangy1.forceUnlock();
            if(zhangy.isLocked()){
                logger.info("result1:解锁失败");
                return;
            }
            logger.info("result1:解锁成功");
        }).start();
        TimeUnit.SECONDS.sleep(5);

        boolean locked = zhangy.isLocked();
        logger.info("locked:locked:{}", locked);
        while (true){
            TimeUnit.SECONDS.sleep(10);
        }
    }*/

    @Test
    public void testMulLock() throws InterruptedException {
        new Thread(() ->{
            boolean result1 = distributeLockClient.lock("zhangy");
            if(result1){
                logger.info("result1:加锁成功");
                return;
            }
            logger.info("result1:加锁失败");
        }).start();

        new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result2 = distributeLockClient.lock("zhangy");
            if(result2){
                logger.info("result2:加锁成功");
                return;
            }
            logger.info("result2:加锁失败");
        }).start();

        TimeUnit.SECONDS.sleep(5);
        logger.info("result1开始解锁");
        distributeLockClient.unlock("zhangy");
        //logger.info("resultUn:{}", resultUn);
        while (true){
            TimeUnit.SECONDS.sleep(10);
        }
    }

    @Test
    public void testId() throws InterruptedException {
        Set<Long> ids = Sets.newConcurrentHashSet();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 200, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8000));
        CountDownLatch countDownLatch = new CountDownLatch(8000);
        for (int i = 0; i < 8000; i++) {
            threadPoolExecutor.execute(()->{
                ids.add(snowFlakeManager.nextLong());
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        logger.info("ids size:{}", ids.size());
        logger.info("finish...");
    }

}
