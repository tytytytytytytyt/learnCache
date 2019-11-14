package com.geotmt.cacheprime.suanfa;

import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Stack;

public class MathOperationBaseStack {

    private Stack<Integer> numStack = new Stack<>();
    private Stack<String> operaionalStack = new Stack<>();

    private void parse(String expression){
        String preOperation = null;
        char[] chars = expression.toCharArray();
        for (int i = 0; i < chars.length ; i++) {
            char ch = chars[i];
            if(!Character.isDigit(ch)){
                numStack.push(Integer.parseInt(String.valueOf(ch)));
            }else {
                String curOperation = String.valueOf(ch);
                if(isPriority(curOperation,preOperation)){

                }
                operaionalStack.push(preOperation);
            }
        }
    }

    private boolean isPriority(String curOperation,String preOperation){

        List priorityList = Lists.newArrayList("*", "/");
        List lowerList = Lists.newArrayList("+", "-");

        return true;

    }



}
