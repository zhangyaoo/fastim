package com.zyblue.fastim.sample.pojo.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 2713277720505225515L;

    private String token;

    private int tcpPort;

    private String tcpUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public String getTcpUrl() {
        return tcpUrl;
    }

    public void setTcpUrl(String tcpUrl) {
        this.tcpUrl = tcpUrl;
    }
}
