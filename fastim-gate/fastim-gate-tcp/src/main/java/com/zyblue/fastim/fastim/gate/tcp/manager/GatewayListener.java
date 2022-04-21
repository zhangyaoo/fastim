package com.zyblue.fastim.fastim.gate.tcp.manager;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author will
 * @date 2021/11/29 16:32
 */

@Deprecated
@Component
public class GatewayListener implements ApplicationListener<ApplicationReadyEvent> {

    private final static Logger log = LoggerFactory.getLogger(GatewayListener.class);

    @Resource
    private ApplicationContext applicationContext;

    @NacosInjected
    private NamingService namingService;

    @Value("${server.port}")
    private int nettyPort;

    public void registerToNacos(){
        Instance instance = new Instance();
        instance.setIp("127.0.0.1");
        instance.setPort(nettyPort);
        //setIpPortFromDubbo(instance);

        try {
            namingService.registerInstance("fastim-gate-tcp", instance);
        } catch (NacosException e) {
            log.error("error:", e);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            //registerToNacos();
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
