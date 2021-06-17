package com.zyblue.fastim.fastim.gate.tcp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZKConfig {
    @Value("${app.zk.addr}")
    private String zkAddr;

    @Value("${app.zk.connect.timeout}")
    private Long timeout;

    @Value("${app.zk.root}")
    private String zkRoot;

    public String getZkAddr() {
        return zkAddr;
    }

    public void setZkAddr(String zkAddr) {
        this.zkAddr = zkAddr;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }
}