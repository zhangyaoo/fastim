package com.zyblue.fastim.fastim.gate.tcp.service.impl;

import com.zyblue.fastim.fastim.gate.tcp.service.MetadataService;

import java.util.List;

/**
 * @author will
 * @date 2021/8/3 16:40
 */
public class NacosMetadataServiceImpl implements MetadataService {
    @Override
    public List<?> getRegistryServiceMetadataList() {
        // 1. 先把单聊和群聊的服务的入参方法注册网关
        // 2. 网关根据cmd类型获取服务名称和方法，然后根据服务名称和方法去元数据中找对应的入参
        // 3. 泛化调用
        return null;
    }

    /*@Override
    public List<RegistryServiceMetadata> getRegistryServiceMetadataList(ServerInfo serverInfo) {
        List<Instance> serviceInstances;
        try {
            serviceInstances = namingService.getAllInstances(EUNOMIA_PREFIX + serverInfo.getAppName() +
                    ":" + serverInfo.getMoudleName() + ":" + serverInfo.getVersion(), true);
        } catch (NacosException | IndexOutOfBoundsException e) {
            throw new EunomiaException(EunomiaStatus.REGISTRY_CENTER_ERR.getCode(), EunomiaStatus.REGISTRY_CENTER_ERR.getMsg());
        }

        if (CollectionUtils.isEmpty(serviceInstances)) {
            throw new EunomiaException(EunomiaStatus.REGISTRY_CENTER_ERR.getCode(), EunomiaStatus.REGISTRY_CENTER_ERR.getMsg());
        }

        List<RegistryServiceMetadata> registryServiceMetadataList = new ArrayList<>(2);
        serviceInstances.forEach(serviceInstance -> {
            RegistryServiceMetadata registryServiceMetadata = new RegistryServiceMetadata();
            registryServiceMetadata.setApplicationIpAndPort(serviceInstance.getIp() + ":" + serviceInstance.getPort());
            String dubboMetadata = serviceInstance.getMetadata().get("dubbo");
            JSONArray rpcAttachmentJsonArray = JSONUtil.parseObj(dubboMetadata).getJSONArray("rpc_attachments");
            List<String> rpcAttachments = JSONUtil.toList(rpcAttachmentJsonArray, String.class);
            registryServiceMetadata.setRpcAttachments(rpcAttachments);
            registryServiceMetadataList.add(registryServiceMetadata);
        });
        return registryServiceMetadataList;
    }*/
}
