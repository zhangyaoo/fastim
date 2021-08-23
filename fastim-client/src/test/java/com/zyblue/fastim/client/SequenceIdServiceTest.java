package com.zyblue.fastim.client;

import com.zyblue.fastim.client.service.SequenceIdService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author will
 * @date 2021/7/28 14:18
 */
@SpringBootTest
public class SequenceIdServiceTest {
    private final static Logger log = LoggerFactory.getLogger(SequenceIdServiceTest.class);

    @Resource
    private SequenceIdService sequenceIdService;

    @Test
    public void test() throws InterruptedException {
        Set<Integer> set = Collections.newSetFromMap(new ConcurrentHashMap());
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                   set.add(sequenceIdService.generateSequenceId());
                }
                countDownLatch.countDown();
                System.out.println("执行完"+ finalI);
            }
            ).start();
        }
        countDownLatch.await();
        log.info("size:{}",set.size());
        log.info("size:{}",set.size());
    }
}
