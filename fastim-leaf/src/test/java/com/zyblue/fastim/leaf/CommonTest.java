package com.zyblue.fastim.leaf;

import com.zyblue.fastim.leaf.manager.SnowFlakeManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
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


    @Test
    public void testId() throws InterruptedException {
        Set<Long> ids = new HashSet<>();

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
