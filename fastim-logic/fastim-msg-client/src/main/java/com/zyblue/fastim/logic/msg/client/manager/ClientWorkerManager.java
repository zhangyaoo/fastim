package com.zyblue.fastim.logic.msg.client.manager;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Maps;
import com.zyblue.fastim.common.pojo.Message;
import com.zyblue.fastim.common.util.JacksonUtils;
import com.zyblue.fastim.logic.msg.client.NettyWebsocketClient;
import com.zyblue.fastim.logic.msg.client.annotation.MsgReceivedListener;
import com.zyblue.fastim.logic.msg.client.holder.ChannelHolder;
import com.zyblue.fastim.logic.msg.client.worker.MsgReceiveHandler;
import com.zyblue.fastim.logic.msg.client.worker.MsgWorker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOutboundInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author will
 * @date 2022/4/12 18:01
 */
public class ClientWorkerManager {

    private static final Logger logger = LogManager.getLogger(ClientWorkerManager.class);

    private final NamingService namingService;

    private final ApplicationContext applicationContext;

    private MsgWorker msgWorker;

    private final NettyWebsocketClient WEBSOCKET_CLIENT = NettyWebsocketClient.buildWebsocketClient();

    public ClientWorkerManager(NamingService namingService, ApplicationContext applicationContext) {
        this.namingService = namingService;
        this.applicationContext = applicationContext;
    }

    public void init() throws NacosException {
        // 连接网关实例
        initWorker();
        // 监听，动态清除和新建连接
        connect();
    }

    private void connect() throws NacosException {
        namingService.subscribe(applicationContext.getApplicationName(), event -> {
            if (!(event instanceof NamingEvent)) {
                logger.warn("is not NamingEvent , continue class:{}", event.getClass());
                return;
            }
            List<Instance> instances = ((NamingEvent) event).getInstances();
            // 找出健康的实例
            List<Instance> healthyInstances = instances.stream().filter(Instance::isHealthy).collect(Collectors.toList());

            if (healthyInstances.size() == 0) {
                logger.error("no available service");
                ChannelHolder.GATE_CHANNEL.clear();
                if (!CollectionUtils.isEmpty(ChannelHolder.GATE_CHANNEL.values())) {
                    ChannelHolder.GATE_CHANNEL.values().forEach(ChannelOutboundInvoker::close);
                }
                return;
            }

            Map<String, Instance> instanceMap = healthyInstances.stream()
                    .collect(Collectors.toMap(instance -> (instance.getIp() + ":" + instance.getPort()), instance -> instance));

            logger.info("instance change，instanceMap:{}", instanceMap);

            // 清除掉线的server
            Iterator<Map.Entry<String, Channel>> iterator = ChannelHolder.GATE_CHANNEL.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Channel> entry = iterator.next();
                if (!instanceMap.containsKey(entry.getKey())) {
                    logger.info("remove instance,{}", entry.getKey());
                    entry.getValue().close();
                    iterator.remove();
                }
            }

            // 连上新的server
            for (Map.Entry<String, Instance> entry : instanceMap.entrySet()) {
                if (!ChannelHolder.GATE_CHANNEL.containsKey(entry.getKey())) {
                    connect(entry.getValue().getIp(), entry.getValue().getPort());
                }
            }
        });
    }

    private String genKey(String ip, int port){
        return ip + ":" + port;
    }

    private void connect(String ip, int port){
        ChannelFuture channelFuture = WEBSOCKET_CLIENT.connect(this::onOpen, this::onMessage, this::onClose, ip, port, Maps.newHashMap());
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                ChannelHolder.GATE_CHANNEL.put(genKey(ip, port), channelFuture.channel());
            } else {
                logger.info("connect fail，1s reconnect ip:{} port:{}", ip, port);
                Throwable cause = future.cause();
                if (cause != null) {
                    logger.info("connect fail", cause);
                }
                reconnect(ip, port);
            }
        });
    }

    private void reconnect(String ip, int port){
        if (!ChannelHolder.GATE_CHANNEL.containsKey(genKey(ip, port))) {
            logger.info("instance remove from nacos ip:{} port:{}", ip, port);
            return;
        }
        WEBSOCKET_CLIENT.schedule(() -> {
            // 已经被删掉了，不用重连，出现在服务实例变更后，重连任务不在进行
            if (!ChannelHolder.GATE_CHANNEL.containsKey(genKey(ip, port))) {
                logger.info("instance remove from nacos ip:{} port:{}", ip, port);
                return;
            }
            logger.info("reconnect ip:{} port:{}", ip, port);
            connect(ip, port);
        }, 1, TimeUnit.SECONDS);
    }

    /**
     * 刚开始创建连接动作
     */
    private void onOpen(Channel channel){

    }

    /**
     * 接收消息
     */
    private void onMessage(Channel channel, String data){
        logger.info("onMessage remoteAddress:{} , content:{} ", channel.remoteAddress(), data);

        Message<List<Map<String, Object>>> message;
        try {
            message = JacksonUtils.json2pojo(data, Message.class);
        } catch (Exception e) {
            logger.error("onMessage transfer error", e);
            return;
        }

        msgWorker.accept(message.getData());
    }

    /**
     * 连接关闭，重新进行连接
     */
    private void onClose(Channel channel){
        Optional<Map.Entry<String, Channel>> optEntry = ChannelHolder.GATE_CHANNEL.entrySet().stream().filter(entry -> entry.getValue().equals(channel)).findFirst();
        //已经被删掉了，不用重连
        if (!optEntry.isPresent()) {
            logger.info("instance remove from nacos, remoteAddress:{}", channel.remoteAddress());
            return;
        }
        String key = optEntry.get().getKey();
        String[] ipPort = key.split(":");
        String ip = ipPort[0];
        int port = Integer.parseInt(ipPort[ipPort.length - 1]);
        logger.info("reconnect ip:{} port:{}", ip, port);
        connect(ip, port);
    }

    private void initWorker() {
        if (applicationContext == null) {
            return;
        }
        // init handler from method
        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            // referred to ：org.springframework.context.event.EventListenerMethodProcessor.processBean
            Map<Method, MsgReceivedListener> annotatedMethods = Maps.newHashMap();
            try {
                annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                        (MethodIntrospector.MetadataLookup<MsgReceivedListener>) method -> AnnotatedElementUtils.findMergedAnnotation(method, MsgReceivedListener.class)
                );
            } catch (Throwable ex) {
                logger.error("import-handler resolve error for bean[" + beanDefinitionName + "].", ex);
            }

            if (annotatedMethods.size() > 1) {
                logger.error("error found more MsgListener method");
                throw new RuntimeException("error found more MsgListener method");
            }
            for (Map.Entry<Method, MsgReceivedListener> entry : annotatedMethods.entrySet()) {
                MsgReceivedListener listener = entry.getValue();
                Method method = entry.getKey();
                method.setAccessible(true);
                this.msgWorker = new MsgWorker(new MsgReceiveHandler(listener, method));
            }
        }
    }
}
