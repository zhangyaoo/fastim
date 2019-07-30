package com.zyblue.fastim.common;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private static final long serialVersionUID = -6250430743260531316L;

    private String ip ;
    private Integer serverPort;
    private Integer httpPort;

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

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
