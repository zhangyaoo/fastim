package com.zyblue.fastim.logic.msg.client;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.zyblue.fastim.logic.msg.client.manager.ClientWorkerManager;
import com.zyblue.fastim.logic.msg.client.manager.RouteInfoManager;
import com.zyblue.fastim.logic.msg.client.worker.MsgClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author will
 * @date 2022/4/12 17:58
 */
@Configuration
public class ClientAutoConfigure {

    @NacosInjected
    private NamingService namingService;

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(ClientWorkerManager.class)
    public ClientWorkerManager worker(ApplicationContext applicationContext){
        return new ClientWorkerManager(namingService, applicationContext);
    }

    @Bean(name = "msgClient")
    public MsgClient msgClient(){
        return new MsgClient(new RouteInfoManager());
    }
}
