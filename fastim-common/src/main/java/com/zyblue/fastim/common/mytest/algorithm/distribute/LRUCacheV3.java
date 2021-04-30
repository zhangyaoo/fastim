package com.zyblue.fastim.common.mytest.algorithm.distribute;

import java.util.HashMap;
import java.util.Map;

/**
 * @author will
 * @date 2021/3/15 10:18
 */
public class LRUCacheV3<K, V>{
    public static class Node<K,V>{
        public K key;
        public V value;
        public Node<K,V> pre;
        public Node<K,V> next;
        public Node(){}
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * map这样设计的原因是
     * 删除节点的时候，可以根据map的value也就是Node的key，来删除map中的数据
     * 也就是说，可以根据map的key来删除数据，也可以根据map的value中的key来删除数据
     */
    private Map<K, Node<K,V>> map = new HashMap<>();

    private int capacity;

    private Node<K,V> head;

    private Node<K,V> tail;

    public LRUCacheV3(int capacity){
        this.capacity = capacity;
        this.head = new Node<>();
        this.tail = new Node<>();
        head.pre = tail;
        tail.next = head;
    }

    private void remove2Head(Node<K,V> node){
        Node<K, V> pre = node.pre;
        Node<K, V> next = node.next;
        pre.next = next;
        next.pre = pre;

        add2Head(node);
    }

    private void removeTail(){
        Node<K, V> next = tail.next.next;
        tail.next = next;
        next.pre = tail;
    }

    private void add2Head(Node<K,V> node){
        Node<K, V> pre = head.pre;
        pre.next = node;
        node.next = head;
        node.pre = pre;
        head.pre = node;
    }

    public V get(K key){
        Node<K,V> node = map.get(key);
        if(node == null){
            return null;
        }
        remove2Head(node);
        return node.value;
    }

    public void put(K key, V value){
        Node<K,V> node = map.get(key);
        if(node == null){
            // 利用map的size
            if(map.size() >= capacity){
                // 去除尾部节点
                removeTail();
                // 删除map的node
                map.remove(tail.next.key);
            }
            Node<K,V> nodeAdd = new Node<>(key, value);
            add2Head(nodeAdd);
            map.put(key, nodeAdd);
        }else {
            node.value = value;
            remove2Head(node);
        }
    }
}
