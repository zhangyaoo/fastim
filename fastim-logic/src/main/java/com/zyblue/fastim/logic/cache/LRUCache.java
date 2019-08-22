package com.zyblue.fastim.logic.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 写字楼里写字间，写字间里程序员
 * 程序人员写程序，又拿程序换酒钱
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
