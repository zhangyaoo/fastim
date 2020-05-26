package com.zyblue.fastim.leaf.service.impl;

import com.zyblue.fastim.leaf.manager.SnowFlakeManager;
import com.zyblue.fastim.leaf.service.LeafService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LeafServiceImpl implements LeafService {

    private final static Logger logger = LoggerFactory.getLogger(LeafServiceImpl.class);


    @Autowired
    private SnowFlakeManager snowFlakeManager;

    @Override
    public Long getDistributedId() {
        logger.info("getDistributedId start");
        return snowFlakeManager.nextLong();
    }
}
