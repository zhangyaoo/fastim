package com.zyblue.fastim.common.mytest.algorithm.tree;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Treemap实现一致性hash算法
 * Author : will 2019.11.18
 * function：主要利用了红黑树的tailMap（key）方法，找到比key大的map集合
 */
public class ConsistentHashTreeMap {

    private TreeMap<Long,String> treeMap = new TreeMap<Long, String>();

    /**
     * 虚拟节点数量
     */
    private static final int VIRTUAL_NODE_SIZE = 2 ;

    public void add(String ip){
        // 每加入一个节点，加入一定数量的虚拟节点
        for (int i = 0; i < VIRTUAL_NODE_SIZE; i++) {
            Long hash = hash("vir" + ip + i);
            treeMap.put(hash,ip);
        }
        treeMap.put(hash(ip), ip);
    }

    public String get(String key){
        Long fromKey = hash(key);
        // 从红黑树中查找比fromKey大的值的集合，不需要遍历整个数据结构
        SortedMap<Long, String> tailMap = treeMap.tailMap(fromKey);
        if (!tailMap.isEmpty()) {
            return tailMap.get(tailMap.firstKey());
        }
        return treeMap.firstEntry().getValue();
    }

    /**
     * hash 运算
     */
    public Long hash(String value){
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + value, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);

        long truncateHashCode = hashCode & 0xffffffffL;
        return truncateHashCode;
    }
}
