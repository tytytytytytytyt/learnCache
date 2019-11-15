package com.geotmt.cacheprime.suanfa;

import com.google.common.base.Joiner;

/**
 * 基于数组实现队列  最基本的操作也是两个：入队enqueue()，放一个数据到 队列尾部；出队dequeue()，从队列头部取一个元素。
 *
 */
public class QueueBaseArray {

    private Object [] contents ;
    private int actuallySize;

    public QueueBaseArray(int lenth) {
        contents = new Object[lenth] ;
    }

    public boolean enQueue(Object element){
        if(actuallySize == contents.length){
            return false;
        }else {
            contents[actuallySize] = element;
            actuallySize++;
            return true;
        }
    }

    public Object deQueue(){
        if(actuallySize == 0){
            return null;
        }else {
            Object element = contents[0];
            //Object src,  int  srcPos,Object dest, int destPos,int length
            Object [] newContent = new Object[actuallySize -1];
            System.arraycopy(contents,1,newContent,0,actuallySize -1);
            contents = newContent;
            actuallySize--;
            return element;
        }
    }

    private void printAll(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < contents.length; i++) {
          sb.append(contents[i]).append(",");
        }
        System.out.println(sb.toString());
    }


    public static void main(String[] args) {

        QueueBaseArray queueBaseArray = new QueueBaseArray(6);
        for (int i = 1; i <= 6; i++) {
            queueBaseArray.enQueue(i);
        }

        queueBaseArray.printAll();

        Object o1 = queueBaseArray.deQueue();
        System.out.println(o1);
        Object o2 = queueBaseArray.deQueue();
        System.out.println(o2);

        queueBaseArray.printAll();

    }

}
