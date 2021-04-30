package com.zyblue.fastim.common.mytest.algorithm.array;

/**
 * 循数组实现循环队列
 * Author : BlueSky 2019.11.05
 * 思路：
 * 1、循环队列和非循环队列类似，但是要注意队列空和队列满的情况
 * 2、队列空 tail = head
 * 3、队列满 (tail+1)%capacity = head
 */
public class ArrayCircularQueue {


    public  static class CircularQueue{

        /**
         * 数组
         */
        private final String[] array;

        /**
         * 容量
         */
        private final Integer capacity;

        /**
         * 头指针
         */
        private Integer head = 0;

        /**
         * 尾指针
         */
        private Integer tail = 0;

        public CircularQueue(Integer capacity) {
            this.array = new String[capacity];
            this.capacity = capacity;
        }

        public boolean add(String data){
            int i = (tail + 1) % capacity;
            if( i == head){
                return false;
            }
            // 赋值
            array[tail] = data;
            // 尾指针复位
            tail = i;
            return true;
        }

        public String poll(){
            if(tail.equals(head)){
                return null;
            }
            // 取值
            String res = array[head];
            // 头指针复位
            head = (head + 1) % capacity;
            return res;
        }
    }
}
