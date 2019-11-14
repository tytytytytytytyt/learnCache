package com.geotmt.cacheprime.suanfa;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Stack;

public class MathOperationBaseStack {

    private Stack<Integer> numStack = new Stack<>();
    private Stack<String> operaionalStack = new Stack<>();
    List priorityList = Lists.newArrayList("*", "/");
    List lowerList = Lists.newArrayList("+", "-");
    private int result = 0 ;

    private void parse(String expression){

        char[] chars = expression.toCharArray();
        for (int i = 0; i < chars.length ; i++) {
            char ch = chars[i];
            if(Character.isDigit(ch)){
                numStack.push(Integer.parseInt(String.valueOf(ch)));
            }else {
                String curOperation = String.valueOf(ch);
                String preOperation = getPreOpertaion();
                if(isPriority(curOperation,preOperation)){
                    compute();
                }
                operaionalStack.push(curOperation);
            }
        }

        int finalResult = compute();
        System.out.println(finalResult);
    }

    private int compute() {
        String operation = operaionalStack.pop();

        while (operation!=null){
            Integer num1 = numStack.pop();
            Integer num2 = getNum2();
            if(num2 != null){
                if(operation.trim().equals("*")){
                    result += num1 * num2;
                }else if(operation.trim().equals("/")){
                    result += num1 / num2;
                }else if(operation.trim().equals("+")){
                    result += num1 + num2;
                }else if(operation.trim().equals("-")){
                    result += num1 - num2;
                }
                operation = operaionalStack.pop();
            }else{
                if(operation.trim().equals("*")){
                    result *= num1;
                }else if(operation.trim().equals("/")){
                    result = num1/result;
                }else if(operation.trim().equals("+")){
                    result += num1;
                }else if(operation.trim().equals("-")){
                    result -= num1;
                }
                operation = null;
            }
        }

        return result;

    }

    private Integer getNum2() {
        try{
            return numStack.pop();
        }catch (Exception e){
            return null;
        }
    }


    private String getPreOpertaion(){
        try{
            String preOperation = operaionalStack.peek();
            System.out.println("~~~~~~~~~~~~~~" + preOperation);
            return preOperation;
        }catch (Exception e){
            return null;
        }

    }

    private boolean isPriority(String curOperation,String preOperation){


        if(priorityList.contains(preOperation) && lowerList.contains(curOperation)){
            return true;
        }
        return false;

    }

    public static void main(String[] args) {

        MathOperationBaseStack mathOperation = new MathOperationBaseStack();
        //mathOperation.parse("1+2*3+3");//10

        //mathOperation.parse("1+3*3+3");//13

        mathOperation.parse("1+3*4+3");//16
    }



}
