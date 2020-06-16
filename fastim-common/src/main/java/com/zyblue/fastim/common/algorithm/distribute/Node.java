package com.zyblue.fastim.common.algorithm.distribute;

/**
 * @author will.zhang
 * @date 2020/6/9 15:44
 */
public class Node {
    private Object data;

    private String key;

    private Node pre;

    private Node next;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", key='" + key;
    }
}
