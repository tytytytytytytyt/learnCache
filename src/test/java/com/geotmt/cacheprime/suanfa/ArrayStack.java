package com.geotmt.cacheprime.suanfa;

public class ArrayStack {

    private Integer [] stack;
    private Integer capacity;
    private Integer position;

    public ArrayStack( Integer capacity) {
        this.stack = new Integer[capacity];
        this.capacity = capacity;
        this.position = 0;
    }


    public boolean push(Integer ele){
        if(position == capacity){
            return false;
        }
        stack[position] = ele;
        position++;
        return true;
    }


    public Integer pop(){
        if(position == 0){
            return  null;
        }
        Integer ele = stack[position -1];
        position--;
        return ele;
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(3);
        arrayStack.push(1);
        arrayStack.push(2);
        arrayStack.push(3);

        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
    }

}
