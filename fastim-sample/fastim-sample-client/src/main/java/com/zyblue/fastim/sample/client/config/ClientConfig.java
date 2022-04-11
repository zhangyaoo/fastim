package com.zyblue.fastim.sample.client.config;

import com.zyblue.fastim.client.ClientManager;
import com.zyblue.fastim.client.WebsocketClientManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author will
 * @date 2021/12/2 14:41
 */
@Configuration
public class ClientConfig {

    @Value("${client.max.connect.retry.time}")
    private Integer connectRetry;

    @Value("${client.max.send.retry.time}")
    private Integer sendRetry;

    @Value("${client.max.send.retry.duration}")
    private Integer sendDuration;

    @Bean
    public WebsocketClientManager websocketClientManager() throws Exception {
        WebsocketClientManager.Builder builder = new WebsocketClientManager.Builder();
        return builder.setSendRetry(sendRetry).setConnectRetry(connectRetry).setSendDuration(sendDuration).build(false);
    }

    @Bean
    public ClientManager clientManager(){
        ClientManager.Builder builder = new ClientManager.Builder();
        return builder.setSendRetry(sendRetry).setConnectRetry(connectRetry).setSendDuration(sendDuration).build();
    }
}
