package com.zyblue.fastim.fastim.gate.tcp.manager;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.dubbo.config.AbstractConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

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

    @Value("${fastim.server.port}")
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


    /**
     * 只能在服务提供者下面才能获取到
     * 尝试从Dubbo中获取对应的ip和port
     * @param instance instance
     */
    private void setIpPortFromDubbo(Instance instance){
        Map<String, AbstractConfig> beansOfType = null;
        try {
            beansOfType = applicationContext.getBeansOfType(AbstractConfig.class);
        } catch (Exception ignored) {
        }
        if (beansOfType != null && beansOfType.size() > 0) {
            Collection<AbstractConfig> values = beansOfType.values();
            for (AbstractConfig abstractConfig : values) {
                if (abstractConfig instanceof ServiceConfig) {
                    ServiceConfig<?> serviceConfig = (ServiceConfig<?>) abstractConfig;
                    int port = serviceConfig.toUrl().getPort();
                    String host = serviceConfig.toUrl().getHost();
                    instance.setIp(host);
                    instance.setPort(port);
                    break;
                }
            }
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
