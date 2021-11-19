package com.zyblue.fastim.sample.manager;

import com.zyblue.fastim.client.ClientStarter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/11/19 16:23
 */
@Component
public class ConnectionManager {

    @Value("${max.connect.retry.time}")
    private Integer connectRetry;

    @Value("${max.send.retry.time}")
    private Integer sendRetry;

    @Value("${max.send.retry.duration}")
    private Integer sendDuration;

    private ClientStarter starter;

    public void connect(String tcpUrl, int tcpPort){
        ClientStarter.Builder builder = new ClientStarter.Builder();
        starter = builder.setTcpUrl(tcpUrl).setTcpPort(tcpPort)
                .setSendRetry(sendRetry).setConnectRetry(connectRetry).setSendDuration(sendDuration).build();
        starter.start();
    }

    public void disConnect(){
        if(starter != null){
            starter.stop();
        }
    }
}
