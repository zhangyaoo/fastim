package com.zyblue.fastim.fastim.lsb.service;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.zyblue.fastim.common.pojo.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author blue
 */
@Service
public class ServerInfoServiceImpl implements ServerInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ServerInfoServiceImpl.class);

    @NacosInjected
    private NamingService namingService;

    @Value("${instance.gate.name}")
    private String instanceName;

    @Override
    public ServerInfo getServerInfo() throws Exception {
        List<Instance> allInstances;
        try {
            allInstances = namingService.getAllInstances(instanceName);
        } catch (NacosException e) {
            logger.error("error:", e);
            throw new Exception("注册中心异常");
        }

        if(CollectionUtils.isEmpty(allInstances)){
            throw new Exception("注册中心未找到服务");
        }

        int index = ThreadLocalRandom.current().nextInt(0, allInstances.size());
        index = Math.min(index, allInstances.size() - 1);
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setIp(allInstances.get(index).getIp());
        serverInfo.setServerPort(allInstances.get(index).getPort());
        return serverInfo;
    }
}
