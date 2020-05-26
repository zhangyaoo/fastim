package com.zyblue.fastim.logic.delayqueue;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zyblue.fastim.logic.manager.DelayQueueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 高性能延迟队列：数组实现循环队列
 */
@Component
public class LoopQueue{

    private final static Logger log = LoggerFactory.getLogger(LoopQueue.class);


    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Resource
    private DelayQueueManager delayQueueManager;

    /**
     * 持久化文件名
     */
    public static final String FILE_NAME = "taskData";

    /**
     * 持久化位置
     */
    public static final String FILE_PATH = "";

    /**
     * 容量
     */
    private int capacity;

    private final int defaultSize = 60;

    /**
     * 数据
     */
    private ArrayList<Node>[] elementData;

    /**
     * 当前指针
     */
    private volatile int currentHead;

    /**
     * 当前指针是否正在执行任务
     */
    private volatile boolean flag = false;

    /**
     * 每分钟变一次指针
     */
    @PostConstruct
    public void init(){
        log.info("DelayQueue-pointer init begin");
        capacity = defaultSize;

        File file = new File(FILE_PATH + File.separator + FILE_NAME);
        if(file.exists()) {
            log.info("read data from file");
            readDataFromFile(file);
            log.info("read data end");
        }else {
            log.info("init..");
            currentHead = 0;
            elementData = new ArrayList[capacity];
        }
        Thread thread = new Thread(() -> run());
        thread.setName("DelayQueue-pointer");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 移动指针
     */
    public void run(){
        while (true){
            log.info("delay queue move index , currentHead:{}", currentHead);

            flag = true;
            ArrayList<Node> nodes = elementData[currentHead];
            if(!CollectionUtils.isEmpty(nodes)){
                log.info("nodes size:{}", nodes.size());

                // 取出要执行的任务
                List<Node> executeNodes = nodes.stream().filter(x->x.getCycleNum() == 0).collect(Collectors.toList());
                log.info("executeNodes size:{}", executeNodes.size());
                if(executeNodes.size() > 0){
                    for(Node node:executeNodes){
                        poolTaskExecutor.execute(()->delayQueueManager.runTask(node.getTask()));
                    }
                }

                /*
                 *  去掉要执行的任务
                 */
                nodes = nodes.stream().filter(x -> x.getCycleNum() > 0).peek(x -> x.setCycleNum(x.getCycleNum() - 1)).collect(Collectors.toCollection(ArrayList::new));
                elementData[currentHead] = nodes;
            }

            flag = false;

            /*
             * 持久化高可用
             */
            poolTaskExecutor.execute(()->{
                try{
                    writeData2File(currentHead, elementData);
                }catch (Exception e){
                    log.error("持久化失败", e);
                }
            });

            try {
                /*
                 * 默认1分钟指针走动一次，1小时转动一圈
                 */
                long defaultInterval = 60 * 1000L;
                Thread.sleep(defaultInterval);
            } catch (Exception e) {
                log.error("e:", e);
                break;
            }

            // volatile关键字happen-before写优先于读
            ++currentHead;
            currentHead = currentHead % capacity;
        }
    }

    /**
     * 读取数据
     */
    public void readDataFromFile(File file){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            HashMap<String, Object> map = (HashMap<String, Object>) in.readObject();
            Integer currentHeadTemp = (Integer)map.get("currentHead");
            ArrayList<Node>[] elementDataTemp = (ArrayList<Node>[])map.get("elementData");
            currentHead = currentHeadTemp == null ? 0 : currentHeadTemp;
            elementData = elementDataTemp == null ? new ArrayList[capacity] : elementDataTemp;
            // 大对象置null
            elementDataTemp = null;
            log.info("readDataFromFile|currentHead:{}", currentHead);
            log.info("readDataFromFile|elementData:{}", toString());
        }catch (Exception e){
            log.error("读取文件数据失败", e);
        }
    }

    /**
     * 持久化
     */
    public void writeData2File (int currentHead, ArrayList<Node>[] elementData) throws Exception{
        log.info("writeData2File begin, currentHead:{}", currentHead);

        File fileDirP = new File(FILE_PATH);
        if(!fileDirP.exists()){
            boolean mkdirsResult = fileDirP.mkdirs();
            log.info("mkdirsResult:{}", mkdirsResult);
        }

        File file = new File(FILE_PATH + File.separator + FILE_NAME);

        if(file.exists()){
            boolean deleteResult = file.delete();
        }else {
            boolean newFile = file.createNewFile();
            log.info("newFile:{}", newFile);
        }

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("currentHead", currentHead);
        map.put("elementData", elementData);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(map);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * 放入任务
     */
    public boolean computeAndPutTask(Long minute, Task task){
        log.info("computeAndPutTask | minute:{}, task:{}", minute, task);
        long index = minute % defaultSize;
        long cycleNum = minute / defaultSize;
        log.info("index:{}, cycleNum:{}", index, cycleNum);

        Node node = new Node();
        node.setTask(task);
        node.setCycleNum(cycleNum);

        /*
         * 如果正在执行取出任务
         * 指针取出动作要先于放入动作，如果错乱导致任务数据出错
         */
        if(index == 0 && flag){
            do {
                log.info("自旋ing...");
                try {
                    Thread.sleep(200);
                }catch (Exception e){
                    log.error("线程睡眠失败", e);
                }
            } while (flag);
        }

        /*
         * 防止多线程增加任务错乱
         */
        synchronized (this){
            ArrayList<Node> nodes = elementData[currentHead + (int) index];
            if(nodes == null){
                nodes = Lists.newArrayList();
            }
            nodes.add(node);
            elementData[currentHead + (int) index] = nodes;
        }

        log.info("computeAndPutTask end");

        return true;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        int length = elementData.length;
        for(int i = 0;i<length-1;i++){
            List<Node> elementDatum = elementData[i];
            if(CollectionUtils.isEmpty(elementDatum)){
                continue;
            }
            stringBuilder.append("index:").append(i).append("===>").append(JSONObject.toJSON(elementDatum));
        }
        return stringBuilder.toString();
    }
}
