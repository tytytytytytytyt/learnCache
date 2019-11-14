package com.geotmt.cacheprime.suanfa;


/**
 * 用数组实现栈
 *
 * 如何理解“栈”？ 关于“栈”，我有一个非常贴切的例子，就是一摞叠在一起的盘子。
 * 我们平时放盘子的时候，都是从下往上一个一个放；取的时候，我们也是从上往下一个一个地 依次取，
 * 不能从中间任意抽出。后进者先出，先进者后出，这就是典型的“栈”结构。
 *
 * 应用：
 *   1、栈在函数调用中的应用
 *   2、栈在表达式求值中的应用
 *          实际上，编译器就是通过两个栈来实现的。其中一个保存操作数的栈，另一个是保存运算符的栈。我们从左向右遍历表达式，当遇到数字，我们就直接压入操作 数栈；
 *          当遇到运算符，就与运算符栈的栈顶元素进行比较。 如果比运算符栈顶元素的优先级高，就将当前运算符压入栈；
 *          如果比运算符栈顶元素的优先级低或者相同，从运算符栈中取栈顶运算符，从操作数栈的栈顶取2个操作数，然后进行计算，再把计算完的结果压入操作数栈，继续比较。
 *   3、栈在括号匹配中的应用
 *
 * 问题：如何实现浏览器的前进、后退功能？
 *     其实，用两个栈就可以非常完美地解决这个问题。
 *     前进x栈, 后退压y栈  每次打开一个网页相当于压入x栈，我们一次打开了3个网页a,b,c
 *     x栈中a,b,c  现在想后退的话，把x栈中的最上面的数据c弹栈，并将c压入y栈，
 *     那么现在b就是x栈最上面的数据了 ，就可以直接查看。点击前进的话把y栈最上面的数据c弹出，
 *     压入x栈，x页面在最上面可以直接观看。
 *
 *
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
