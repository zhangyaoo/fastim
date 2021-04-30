package com.zyblue.fastim.common.mytest.algorithm.distribute;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性HASH：treeMap实现
 * 1）保证key天然排序
 * 2）API tailMap方法能够返回比某个key大的部分数据
 * 3）取tailMap第一个节点就是需要的节点了
 */
public class ConsistenceHash {

    public String getHost(Long hash){
        TreeMap<Long, String> treeMap = new TreeMap<Long, String>();
        treeMap.put(100L, "192.168.0.1");
        treeMap.put(300L, "192.168.0.2");
        treeMap.put(600L, "192.168.0.3");
        treeMap.put(700L, "192.168.0.4");

        String ip;
        SortedMap<Long, String> longStringSortedMap = treeMap.tailMap(hash);
        if(longStringSortedMap.isEmpty()){
            ip = treeMap.firstEntry().getValue();
        }else {
            ip = longStringSortedMap.get(longStringSortedMap.firstKey());
        }

        return ip;
    }
}
