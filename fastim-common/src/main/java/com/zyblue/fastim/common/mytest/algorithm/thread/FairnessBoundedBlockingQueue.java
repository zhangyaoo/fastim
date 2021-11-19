package com.zyblue.fastim.common.mytest.algorithm.thread;

/**
 * @author will
 * @date 2021/11/13 10:56
 */
public class FairnessBoundedBlockingQueue{
    // 容量
    protected final int capacity;

    // 头指针，empty: head.next == tail == null
    protected Node head;

    // 尾指针
    protected Node tail;

    // guard: canPollCount, head
    protected final Object pollLock = new Object();
    protected int canPollCount;
    protected int waitPollCount;

    // guard: canOfferCount, tail
    protected final Object offerLock = new Object();
    protected int canOfferCount;
    protected int waitOfferCount;

    public FairnessBoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.canPollCount = 0;
        this.canOfferCount = capacity;
        this.waitPollCount = 0;
        this.waitOfferCount = 0;
        this.head = new Node(null);
        this.tail = head;
    }

    // 如果队列已满，通过返回值标识
    public boolean offer(Object obj) throws InterruptedException {
        synchronized(offerLock) {
            while(canOfferCount <= 0) {
                waitOfferCount++;
                offerLock.wait();
                waitOfferCount--;
            }
            Node node = new Node(obj);
            tail.next = node;
            tail = node;
            canOfferCount--;
        }
        synchronized(pollLock) {
            ++canPollCount;
            if (waitPollCount > 0) {
                pollLock.notify();
            }
        }
        return true;
    }

    // 如果队列为空，阻塞等待
    public Object poll() throws InterruptedException {
        Object result;
        synchronized(pollLock) {
            while(canPollCount <= 0) {
                waitPollCount++;
                pollLock.wait();
                waitPollCount--;
            }

            result = head.next.value;
            head.next.value = null;
            head = head.next;
            canPollCount--;
        }
        synchronized(offerLock) {
            canOfferCount++;
            if (waitOfferCount > 0) {
                offerLock.notify();
            }
        }
        return result;
    }
    // 省略 Node 的定义

    class Node {
        Object value;
        Node next;
        Node(Object obj) {
            this.value = obj;
            next = null;
        }
    }
}

