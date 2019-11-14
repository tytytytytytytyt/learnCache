package com.geotmt.cacheprime.suanfa;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Scanner;

/**
 * 基于LinkedList实现LRU
 *
 * 我的思路是这样的：我们维护一个有序单链表，越靠近链表尾部的结点是越早之前访问的。当有一个新的数据被访问时，我们从链表头开始顺序遍历链表。
 * 1.如果此数据之前已经被缓存在链表中了，我们遍历得到这个数据对应的结点，并将其从原来的位置删除，然后再插入到链表的头部。
 *
 * 2.如果此数据没有在缓存链表中，又可以分为两种情况：
 *      如果此时缓存未满，则将此结点直接插入到链表的头部；
 *      如果此时缓存已满，则链表尾结点删除，将新的数据结点插入链表的头部。
 */
public class LRUBaseLinkedList<T> {
    // 最大链表长度
    private static final int max_size = 10;
    // 头结点
    private SNode<T> header;
    // 链表的实际长度
    private int size;

    // A->B->C->D
    private void add(T t){
        if(header == null){
            header = new SNode(t,null);
        }else if(size < max_size){
            addNode(t);
        }else{ // size = max_size;
            addNode(t);
            removeOdd();
        }
    }

    private void removeOdd() {
        if(size > max_size){
            SNode preLast = getPreLast();
            removeLast(preLast);
        }
    }

    private void removeLast(SNode preLast) {
        preLast.next = null;
    }


    private void print(){
        StringBuffer sb = new StringBuffer();
        SNode node = header;
        while (node.hasNext()){
            sb.append(node.element).append(",");
            node = node.next;
        }

        System.out.println(sb.toString());
    }



    // A->B->C->D
    private SNode getPreLast(){
        SNode curNode = header;
        SNode last = null;
        while (curNode.hasNext() && curNode.next.hasNext()){
            last = curNode;
            curNode = curNode.next;
        }
        return last;
    }


    private void addNode(T t) {
        LRU lru = isExistNode(t);
        if (lru != null) {
            replaceExitNode(lru);
        }else {
            SNode<T> tsNode = new SNode<>(t, header);
            header = tsNode;
            size ++;
        }
    }

    private  LRU isExistNode(T t){

        SNode pre = null;
        SNode after = null;
        SNode cur = header;

        while (!(t == cur.element || t.equals(cur.getElement()))) {
            if (cur.hasNext()) {
                pre = cur;
                cur = cur.next();
            } else {
                return null;
            }
            after = cur.next;
        }

        return new LRU(pre, cur, after);
    }



    private void replaceExitNode(LRU lru) {
        SNode pre;
        SNode cur;
        SNode after;
        pre = lru.pre;
        cur = lru.cur;
        after = lru.after;

        pre.next = after;
        cur.next = header;
        header = cur;
    }





    @AllArgsConstructor
    static class LRU {
        SNode pre;
        SNode cur;
        SNode after;
    }


    @Data
    static class SNode<T>{

        private T element;
        private SNode next;

        public SNode(T element, SNode sNode) {
            this.element = element;
            this.next = sNode;
        }

        public SNode(T element) {
            this.element = element;
        }

        public boolean hasNext() {
            return next != null ;
        }

        public SNode next(){
            return next;
        }
    }

    public static void main(String[] args) {
        LRUBaseLinkedList list = new LRUBaseLinkedList();
        Scanner sc = new Scanner(System.in);
        while (true) {
            list.add(sc.nextInt());
            list.print();
        }
    }

}
