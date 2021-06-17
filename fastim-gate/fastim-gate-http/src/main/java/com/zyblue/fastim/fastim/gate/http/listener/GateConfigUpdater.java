package com.zyblue.fastim.fastim.gate.http.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.google.common.collect.Maps;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author will
 * @date 2021/6/13 0:20
 */
@Component
public class GateConfigUpdater {
    public final Map<String, Listener> listenerMapping = Maps.newHashMap();

    private final ThreadPoolTaskExecutor synchronizerThread;

    public GateConfigUpdater(ThreadPoolTaskExecutor synchronizerThread){
        this.synchronizerThread = synchronizerThread;
    }

    @PostConstruct
    public void initMapping(){
        listenerMapping.put("http-gate-auth", new Listener() {
            @Override
            public Executor getExecutor() {
                return synchronizerThread;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                // TODO 拿到最新数据后放入缓存，插件信息都是读取缓存配置
                // updateAuthInfo(); 需要比较configInfo中的数据版本和缓存是否一致
            }
        });

        listenerMapping.put("http-gate-route", new Listener() {
            @Override
            public Executor getExecutor() {
                return synchronizerThread;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                // updateAuthInfo();
            }
        });

        listenerMapping.put("http-gate-config", new Listener() {
            @Override
            public Executor getExecutor() {
                return synchronizerThread;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                // updateAuthInfo();
            }
        });
    }
}
