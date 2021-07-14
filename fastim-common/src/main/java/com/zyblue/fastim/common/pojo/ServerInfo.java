package com.zyblue.fastim.common.pojo;


import java.io.Serializable;

public class ServerInfo implements Serializable {
    private static final long serialVersionUID = -6250430743260531316L;

    /**
     * IP信息，如果IP信息为空，那么就用域名
     */
    private String ip;

    /**
     * 服务器域名
     */
    private Integer serverDomain;

    /**
     * 服务器端口
     */
    private Integer serverPort;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(Integer serverDomain) {
        this.serverDomain = serverDomain;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "ip='" + ip + '\'' +
                ", serverDomain=" + serverDomain +
                ", serverPort=" + serverPort +
                '}';
    }
}
