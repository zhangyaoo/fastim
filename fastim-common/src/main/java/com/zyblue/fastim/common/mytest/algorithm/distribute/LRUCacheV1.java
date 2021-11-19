package com.zyblue.fastim.common.mytest.algorithm.distribute;

import java.util.HashMap;

/**
 * @author will.zhang
 * @date 2020/5/26 17:34
 *
 * 手动实现一个LRU算法，不借助JDK工具
 *
 * 数组实现思路：
 * 1、采用了与 HashMap 一样的保存数据方式，只是自己手动实现了一个简易版。
 * 2、内部采用了一个队列来保存每次写入的数据。
 * 3、写入的时候判断缓存是否大于了阈值 N，如果满足则根据队列的 FIFO 特性将队列头的数据删除。因为队列头的数据肯定是最先放进去的。
 * 4、再开启了一个守护线程用于判断最先放进去的数据是否超期（因为就算超期也是最先放进去的数据最有可能满足超期条件。）
 * 5、设置为守护线程可以更好的表明其目的（最坏的情况下，如果是一个用户线程最终有可能导致程序不能正常退出，因为该线程一直在运行，守护线程则不会有这个情况。）
 */
public class LRUCacheV1<K, V> {
    private HashMap<K, Node<K, V>> map = new HashMap<>();
    private int capacity;
    private Node<K, V> head;
    private Node<K, V> tail;

    public LRUCacheV1(int capacity){
        this.capacity = capacity;
        head = new Node<K, V>();
        tail = new Node<K, V>();
        head.pre = tail;
        tail.next = head;
    }

    public void put(K key, V value){
        Node<K, V> node = map.get(key);
        if(node == null){
            if(map.size() == capacity){
                removeTail();
                map.remove(tail.key);
            }
            Node<K, V> node1 = new Node<>();
            node1.setValue(value);
            map.put(key, node1);
            add2Head(node1);
        }else {
            node.setValue(value);
            remove2Head(node);
        }
    }

    public V get(K key){
        Node<K, V> node = map.get(key);
        if(node == null){
            return null;
        }

        remove2Head(node);
        return node.getValue();
    }

    private void remove2Head(Node<K, V> node){
        Node<K, V> next = node.getNext();
        Node<K, V> pre = node.getPre();
        next.pre = pre;
        pre.next = next;

        add2Head(node);
    }

    private void add2Head(Node<K, V> node){
        Node<K, V> pre = head.pre;
        pre.next = node;
        node.pre = pre;

        node.next = head;
        head.pre = node;
    }

    private void removeTail(){
        Node<K, V> next = tail.next.next;
        next.pre = tail;
        tail.next = next;
    }





















    private static class Node<K, V>{
        Node<K, V> pre;
        Node<K, V> next;
        K key;
        V value;

        public Node<K, V> getPre() {
            return pre;
        }

        public void setPre(Node<K, V> pre) {
            this.pre = pre;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
