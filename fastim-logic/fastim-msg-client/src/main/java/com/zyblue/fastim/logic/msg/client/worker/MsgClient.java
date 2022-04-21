package com.zyblue.fastim.logic.msg.client.worker;

import com.alibaba.nacos.common.utils.Pair;
import com.zyblue.fastim.common.pojo.Message;
import com.zyblue.fastim.logic.msg.client.holder.ChannelHolder;
import com.zyblue.fastim.logic.msg.client.manager.RouteInfoManager;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author will
 * @date 2022/4/13 17:59
 */
public class MsgClient {

    private final RouteInfoManager routeInfoManager;

    /**
     * 每100ms发送一次数据给网关
     */
    private static final int PERIOD = 100;

    /**
     * 每个网关所有的请求
     */
    private final Map<String, List<Message<?>>> mapTrue = new ConcurrentHashMap<>();

    /**
     * 每个网关所有的请求
     */
    private final Map<String, List<Message<?>>> mapFalse = new ConcurrentHashMap<>();

    public MsgClient(RouteInfoManager routeInfoManager) {
        this.routeInfoManager = routeInfoManager;
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("MsgSendThread");
            return thread;
        });
        scheduledExecutorService.scheduleAtFixedRate(new SendMsgThread(), 0, PERIOD, TimeUnit.MILLISECONDS);
    }

    private final AtomicBoolean switchQ = new AtomicBoolean(false);

    public void send(Message<?> message, Long userId){
        Pair routeInfo = routeInfoManager.getRouteInfo(userId);
        String route = genKey(routeInfo.getKey(), Integer.parseInt(routeInfo.getValue()));
        if(switchQ.get()){
            List<Message<?>> queue = mapTrue.get(route);
            if(queue == null){
                queue= new LinkedList<>();
            }
            queue.add(message);
            mapTrue.put(route, queue);
        }else {
            List<Message<?>> queue = mapFalse.get(route);
            if(queue == null){
                queue= new LinkedList<>();
            }
            queue.add(message);
            mapFalse.put(route, queue);
        }
    }

    public class SendMsgThread implements Runnable {
        @Override
        public void run() {
            if(switchQ.get()){
                switchQ.set(false);
                sendMsg(mapTrue);
            }else {
                switchQ.set(true);
                sendMsg(mapFalse);
            }
        }

        private void sendMsg(Map<String, List<Message<?>>> map){
            for (Map.Entry<String, List<Message<?>>> entry : map.entrySet()) {
                if(ChannelHolder.GATE_CHANNEL.containsKey(entry.getKey())){
                    // 有路由信息
                    BinaryWebSocketFrame binaryWebSocketFrame = new BinaryWebSocketFrame();
                    // TODO
                    ChannelHolder.GATE_CHANNEL.get(entry.getKey()).writeAndFlush(binaryWebSocketFrame);
                }else {
                    // 没有路由信息
                    // 发送一个延迟任务等会查路由信息，如果没有那么走离线逻辑
                }
            }
        }
    }

    private String genKey(String ip, int port){
        return ip + ":" + port;
    }

    public void sendGroup(Message<?> message, List<Long> userIds){

    }
}
