package com.geotmt.cacheprime.suanfa;




import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class NumCalculate {

    private static Stack<Integer> numStack = new Stack<>();
    private static Stack<String>  opertaStack = new Stack<>();
    static List<String> opertionList = Arrays.asList("+","-","*","/");

    public static Integer calcalate(String express){
        String[] splitArr = express.split(" ", -1);
        Integer resultVal = null;

        for (String content : splitArr) {
            if(isNumeric(content)){
                numStack.push(Integer.valueOf(content));
            }else{
                if(!opertionList.contains(content)){
                    new RuntimeException("参数错误");
                }else{
                    if(opertaStack.isEmpty()){
                        opertaStack.push(content);
                    }else {
                       if(isLowerWithTopStack(content, opertaStack.peek())){
                           while (!numStack.isEmpty()){
                               resultVal += calcalateFromStack(resultVal);
                           }
                       }else{
                           opertaStack.push(content);
                       }
                    }
                }
            }
        }
        return resultVal;
    }

    public static void main(String[] args) {
        String expression = "6 + 2 * 3 + 4";
        Integer calcalate = calcalate(expression);
        System.out.println(calcalate);
    }


    private static Integer calcalateFromStack(Integer valueFromStack) {
        Integer stackNum1 = numStack.pop();
        Integer stackNum2 ;
        if(valueFromStack == null){
            stackNum2 = numStack.pop();
        }else{
            stackNum2 = valueFromStack;
        }

        String opertiaon = opertaStack.pop();

        Integer returnVal = 0;
        if(opertiaon.equals("+")){
            returnVal = stackNum2 + stackNum1;
        }else if(opertiaon.equals("-")){
            returnVal = stackNum2 - stackNum1;
        }else if(opertiaon.equals("*")){
            returnVal = stackNum2 * stackNum1;
        }else if(opertiaon.equals("/")){
            returnVal = stackNum2 / stackNum1;
        }

        numStack.push(returnVal);
        return returnVal;
    }

    public static boolean isNumeric(String str){
        if(str != null && !str.trim().equals("")){
           return str.matches("^[0-9]*$");
        }
        return false;
    }


    // 如果比运算符栈顶元素的优先级低或者相同
    public static boolean isLowerWithTopStack(String opertion,String topStackElement){
        List<String> lowerLever = Arrays.asList("+", "-");
        List<String> heightLever = Arrays.asList("*","/");

        if(lowerLever.contains(opertion) && lowerLever.contains(topStackElement)){
            return true;
        }else if(heightLever.contains(opertion) && heightLever.contains(topStackElement)){
            return true;
        }else if(heightLever.contains(topStackElement) && lowerLever.contains(opertion)){
            return true;
        }

        return false;

    }


}
