package com.zyblue.fastim.client;


import com.zyblue.fastim.client.client.FastImClient;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 客户端启动器
 * @author will
 */
public class ClientManager extends BaseClientManager{
    private FastImClient client;
    private static ClientManager INSTANCE;
    public static ClientManager getInstance() {
        return INSTANCE;
    }

    public ClientManager() {
    }

    @Override
    public boolean connect(String host, Integer port) {
        channel = client.connect(host, port, this::connectFuture);
        this.host = host;
        this.port = port;
        return true;
    }

    @Override
    public void disconnect() {
        super.disconnect();
        client.destroy();
    }

    @Override
    public void reconnect() {
        channel = client.connect(host, port, this::connectFuture);
    }

    public static class Builder {
        private int sendDuration;
        private int sendRetry;
        private int connectRetry;

        public Builder() {
        }

        public Builder setSendDuration(int sendDuration) {
            this.sendDuration = sendDuration;
            return this;
        }


        public Builder setSendRetry(int sendRetry) {
            this.sendRetry = sendRetry;
            return this;
        }


        public Builder setConnectRetry(int connectRetry) {
            this.connectRetry = connectRetry;
            return this;
        }

        public ClientManager build() {
            ClientManager clientManager = new ClientManager();
            clientManager.sendDuration = sendDuration;
            clientManager.sendRetry = sendRetry;
            clientManager.connectRetry = connectRetry;
            clientManager.client = new FastImClient();
            clientManager.ackGroup = new NioEventLoopGroup(1);
            ClientManager.INSTANCE = clientManager;
            return clientManager;
        }
    }
}
