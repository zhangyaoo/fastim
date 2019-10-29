package com.zyblue.fastim.gate.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Autowired
    private ZKConfig zKConfig;

    @Bean
    public ZkClient buildZKClient(){
        return new ZkClient(zKConfig.getZkAddr(), zKConfig.getTimeout().intValue());
    }
}
