package com.zyblue.fastim.common.response;

import com.zyblue.fastim.common.ServerInfo;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 2713277720505225515L;

    private String token;

    private ServerInfo serverInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", serverInfo=" + serverInfo +
                '}';
    }
}
