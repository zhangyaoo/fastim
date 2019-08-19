package com.zyblue.fastim.gate.leaf;

import com.zyblue.fastim.gate.config.ZKConfig;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NodeProcessor {

    private final static Logger logger = LoggerFactory.getLogger(NodeProcessor.class);

    /**
     * leaf的持久化节点
     **/
    private static final String LEAF_FOREVER = "/forever";
    /**
     * leaf的临时节点
     **/
    private static final String LEAF_TEMPORARY = "/temporary";

    /**
     * 心跳时间间隔
     */
    private static final Integer HEARTBEAT_DELAY = 3;

    /**
     * workerId 存储的位置
     */
    private static final String WORKERID_PATH = "/tmp";

    @Autowired
    private ZKConfig zkConfig;

    @Autowired
    private ZkClient zkClient;

    @Value("${server.port}")
    private int serverPort ;

    private static Long lastUpdateTime;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void init(){
        boolean exists = zkClient.exists(zkConfig.getZkRoot() + LEAF_FOREVER);
        if(!exists){
            zkClient.createPersistent(zkConfig.getZkRoot() + LEAF_FOREVER, true);
            scheduledExecutorService.scheduleWithFixedDelay(new ZkHeartBeatTask(), 1, HEARTBEAT_DELAY, TimeUnit.SECONDS);
        }else {
            List<String> children = zkClient.getChildren(zkConfig.getZkRoot() + LEAF_FOREVER);


        }
    }

    private void witreWorkerId2Local(String workerId){

    }

    public static class ZkHeartBeatTask implements Runnable{
        @Override
        public void run() {
            logger.info("upload local time to Zookeeper");
            if(System.currentTimeMillis() < lastUpdateTime){
                logger.info("lastUpdateTime > now");
                return;
            }

        }
    }
}
