package com.zyblue.fastim.common.mytest.algorithm.distribute;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap实现LRU算法
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int  MAX_SIZE;

    public LRUCache(int cacheSize){
        // true 表示让 linkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部。
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        MAX_SIZE = cacheSize;
    }

    @Override
    public boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size() > MAX_SIZE;
    }
}
