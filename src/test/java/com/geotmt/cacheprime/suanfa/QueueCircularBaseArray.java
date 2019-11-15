package com.geotmt.cacheprime.suanfa;

public class QueueCircularBaseArray {

    private Object [] contents ;
    private int capacity;
    private int head = 0;
    private int tail = 0;

    public QueueCircularBaseArray(int capacity) {
        this.capacity = capacity;
        contents = new Object[capacity];
    }

    public boolean enqueue(Object object){
        // 队列满了
        if ((tail + 1) % capacity == head) {
            return false;
        } else {
            contents[tail] = object;
            tail = (tail + 1) % capacity;
            return true;
        }
    }

    public Object dequeue(){
        if (head == tail) {
            return null;
        } else {
            Object o = contents[head];
            contents[head] = null;
            head = (head + 1) % capacity;
            return o;
        }
    }


    private void printAll(){
        for (int i = head; i % capacity != tail; i = (i + 1) % capacity) {
            System.out.print(contents[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        QueueCircularBaseArray queue = new QueueCircularBaseArray(4);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");

        queue.printAll();
        queue.dequeue();
        System.out.println("=====================================");
        queue.printAll();
    }

}
