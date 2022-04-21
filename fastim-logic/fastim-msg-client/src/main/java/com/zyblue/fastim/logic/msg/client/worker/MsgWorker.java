package com.zyblue.fastim.logic.msg.client.worker;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2022/4/13 11:08
 */
public class MsgWorker {

    private static final Logger logger = LogManager.getLogger(MsgWorker.class);

    private final MsgReceiveHandler handler;

    private final Executor executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("MsgWorker").build());

    public MsgWorker(MsgReceiveHandler msgReceiveHandler) {
        this.handler = msgReceiveHandler;
    }

    public void accept(List<Map<String, Object>> data){
        if(handler == null){
            logger.error("");
            throw new RuntimeException("");
        }
        executorService.execute(() -> handler.execute(data));
    }
}
