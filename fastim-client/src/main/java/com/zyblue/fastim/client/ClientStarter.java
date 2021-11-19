package com.zyblue.fastim.client;


import com.zyblue.fastim.client.client.FastImClient;

/**
 * 客户端启动器
 *
 * @author wuweifeng wrote on 2019-12-05
 * @version 1.0
 */
public class ClientStarter {

    private int tcpPort;
    private String tcpUrl;
    private int sendDuration;
    private int sendRetry;
    private int connectRetry;

    public ClientStarter() {
    }

    public static class Builder {
        private int tcpPort;
        private String tcpUrl;
        private int sendDuration;
        private int sendRetry;
        private int connectRetry;

        public Builder() {
        }

        public Builder setTcpPort(int tcpPort) {
            this.tcpPort = tcpPort;
            return this;
        }


        public Builder setTcpUrl(String tcpUrl) {
            this.tcpUrl = tcpUrl;
            return this;
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

        public ClientStarter build() {
            ClientStarter clientStarter = new ClientStarter();
            clientStarter.tcpPort = tcpPort;
            clientStarter.tcpUrl = tcpUrl;
            clientStarter.sendDuration = sendDuration;
            clientStarter.sendRetry = sendRetry;
            clientStarter.connectRetry = connectRetry;

            return clientStarter;
        }

    }


    /**
     * 启动
     */
    public void start() {
        FastImClient instance = FastImClient.getInstance();
        instance.setSendRetry(sendRetry);
        instance.setConnectRetry(connectRetry);
        instance.setSendDuration(sendDuration);
        FastImClient.getInstance().connect(tcpUrl, tcpPort, connectRetry);
    }

    public void stop() {
        FastImClient.getInstance().destroy();
    }
}
