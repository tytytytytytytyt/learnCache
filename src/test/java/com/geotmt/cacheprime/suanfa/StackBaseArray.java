package com.geotmt.cacheprime.suanfa;


/**
 * 用数组实现栈
 */
public class StackBaseArray {

    private static final int STACK_SIZE = 4;
    private int index ;
    private Object [] array = new Object [STACK_SIZE];


    public boolean push(Object o){
        if(index == STACK_SIZE){
            return false;
        }else {
            array[index] = o;
            index++;
            return true;
        }
    }

    public Object pop(){
        if(index == 0){
            return null;
        }else {
            return array[--index];
        }
    }


    public static void main(String[] args) {

        StackBaseArray stack= new StackBaseArray();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");

        Object pop = stack.pop();
        System.out.println(pop);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        stack.push("e");
        for (int i = 0; i < 6; i++) {
            Object pop1 = stack.pop();
            System.out.println(pop1);
        }


    }

}
