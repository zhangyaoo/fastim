package com.zyblue.fastim.gate.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class BeanConfig {

    @Autowired
    private ZKConfig zKConfig;

    @Bean
    public ZkClient buildZKClient(){
        return new ZkClient(zKConfig.getZkAddr(), zKConfig.getTimeout().intValue());
    }
}
