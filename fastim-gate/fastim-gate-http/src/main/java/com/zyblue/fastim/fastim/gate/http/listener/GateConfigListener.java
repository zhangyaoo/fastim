package com.zyblue.fastim.fastim.gate.http.listener;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author will
 * @date 2021/6/9 23:30
 *
 * 单独开一个线程,通过监听注册中心来做DB和Cache的同步
 */
@Component
public class GateConfigListener implements ApplicationListener<ApplicationReadyEvent> {
    private final static Logger logger = LoggerFactory.getLogger(GateConfigListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
        GateConfigUpdater configListener = applicationContext.getBean(GateConfigUpdater.class);
        ConfigService configService = applicationContext.getBean(ConfigService.class);

        try {
            for (Map.Entry<String, Listener> entry : configListener.listenerMapping.entrySet()) {
                configService.addListener(entry.getKey(), "fastIM", entry.getValue());
            }
        } catch (NacosException e) {
            logger.error("NacosException:", e);
            throw new RuntimeException("naocs add Listener fail");
        }
    }
}
