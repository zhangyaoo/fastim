package com.zyblue.fastim.common.mytest.algorithm.array;


/**
 * 数组实现队列
 * Author : BlueSky 2019.11.01
 * 思路：
 * 1、利用双指针分别指向头结点和尾结点来控制先进先出的特性。
 * 2、每次出队操作，将后面的数据向前移一位，来解决头和尾指针向后移动以至于不能插入新数据的问题，但是时间复杂度比较高，出队时间复杂度会由O(1) 变为 O(n)，不建议采用。
 * 3、每次入队操作，判断如果尾结点达到数组最后面，就把头结点和尾节点所有数据移动到数组最前面。时间复杂度还是O(1) 。
 */
public class ArrayQueueMain {



    /*public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(6);
        Boolean flag1 = arrayQueue.addV2("1");
        Boolean flag2 = arrayQueue.addV2("2");
        Boolean flag3 = arrayQueue.addV2("3");
        Boolean flag4 = arrayQueue.addV2("4");
        Boolean flag5 = arrayQueue.addV2("5");

        System.out.println("add res:" +flag1 +","  +flag2 +","+flag3 +","+flag4 +","+flag5  +";");

        String poll1 = arrayQueue.poll();
        String poll2 = arrayQueue.poll();
        System.out.println("poll res:" +poll1 +","  + poll2 + ";");


        Boolean flag6 = arrayQueue.addV2("6");
        Boolean flag7 = arrayQueue.addV2("7");
        Boolean flag8 = arrayQueue.addV2("8");
        Boolean flag9 = arrayQueue.addV2("9");
        Boolean flag10 = arrayQueue.addV2("10");
        System.out.println("add res:" +flag6 +","  +flag7 +","+flag8 +","+flag9 +","+flag10  +";");
    }*/

    public static class ArrayQueue{
        /**
         * 数组
         */
        private String[] array;

        /**
         * 容量
         */
        private Integer capacity;

        /**
         * 头指针
         */
        private Integer head = 0;

        /**
         * 尾指针
         */
        private Integer tail = 0;

        public ArrayQueue(Integer n){
            array = new String[n];
            capacity = n;
        }

        /**
         * 入队V1
         */
        public Boolean addV1(String data){
            // 如果尾节点等于容量，相当于满了
            if(tail.equals(capacity)){
                return false;
            }
            array[tail] = data;
            ++tail;
            return true;
        }

        /**
         * 入队V2
         */
        public Boolean addV2(String data){
            System.out.println("head:" + head);
            System.out.println("tail:" + tail);
            // 如果尾节点等于容量就需要做迁移操作
            if(tail.equals(capacity)){
                // 如果头结点还在第一个位置说明队列已满
                if(head == 0){
                    System.out.println("queue is full");
                    return false;
                }
                // 数据迁移
                System.out.println("data move");
                for(int i = 0; i< tail - head; i++){
                    array[i] = array[head + i];
                }
                // 迁移后需要将指针复位
                tail = capacity - head;
                head = 0;
                System.out.println("move after tail:" + tail);
                // 打印
                for(int i = 0; i< capacity; i++){
                    System.out.println("move after print array:" + array[i]);
                }

            }
            array[tail] = data;
            ++tail;

            for(int i = 0; i< capacity; i++){
                System.out.println("print array:" + array[i]);
            }
            return true;
        }

        /**
         * 出队
         */
        public String poll() {
            if(head.equals(tail)){
                return null;
            }
            String res = array[head];
            ++head;
            return res;
        }
    }
}
