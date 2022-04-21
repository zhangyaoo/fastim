package com.zyblue.fastim.logic.msg.client.manager;

import com.alibaba.nacos.common.utils.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author will
 * @date 2022/4/13 19:56
 * 路由管理
 * 两种路由管理方式
 * 1、redis同步到本地内存
 * 2、gate网关广播路由地址
 */
public class RouteInfoManager {

    /**
     * 用户ID 网关地址的映射
     */
    private final Map<Long, String> routeInfo = new ConcurrentHashMap<>();

    public void addRouteInfo(Long userId, String ip, int port){
        routeInfo.put(userId, genKey(ip, port));
    }

    public Pair getRouteInfo(Long userId){
        String info = routeInfo.get(userId);
        String[] split = info.split(":");
        return new Pair(split[0], split[1]);
    }

    private String genKey(String ip, int port){
        return ip + ":" + port;
    }
}
