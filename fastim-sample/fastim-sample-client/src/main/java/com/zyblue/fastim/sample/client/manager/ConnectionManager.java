package com.zyblue.fastim.sample.client.manager;

import com.zyblue.fastim.client.ClientManager;
import com.zyblue.fastim.client.WebsocketClientManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author will
 * @date 2021/11/19 16:23
 */
@Component
public class ConnectionManager {
    @Resource
    private ClientManager clientManager;

    @Resource
    private WebsocketClientManager websocketClientManager;

    public boolean connect(String host, int port) {
        return clientManager.connect(host, port);
    }

    public void disConnect() {
        if (clientManager != null) {
            clientManager.disconnect();
        }
    }

    public boolean connectWebSocket(String host, int port) {
        return websocketClientManager.connect(host, port);
    }

    public void disConnectWebSocket() {
        if (websocketClientManager != null) {
            websocketClientManager.disconnect();
        }
    }
}
