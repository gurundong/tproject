package com.gurundong.threadproject.thread.mianshi;

import java.util.Stack;

// 栈的弹出、压入队列
// 题目：输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列
// 链接：https://leetcode-cn.com/problems/zhan-de-ya-ru-dan-chu-xu-lie-lcof
// 结题思路：不一定是全部压入栈之后再弹出，也可以是压几个弹一个，再压入，再弹出。那4,5,3,2,1举例的话，就是先压入了1,2,3,4，弹出4，再压入5，再弹出所有
public class Q29 {
    public static void main(String[] args) {
        int[] a1 = {1,2,3,4,5};
        int[] a2 = {4,3,5,1,2};
        System.out.println(sulotion1(a1,a2));

        int[] a11 = {1,2,3,4,5};
        int[] a22 = {4,5,3,2,1};
        System.out.println(sulotion1(a11,a22));

        int[] a3 = {};
        int[] a4 = {};
        System.out.println(sulotion1(a3,a4));
    }

    // 辅助栈法
    public static boolean sulotion1(int[] a1,int[] a2){
        // 注意边界条件
        if(a1 == null || a2 == null  || a1.length != a2.length){
            return false;
        }
        if(a1.length == 0 && a2.length == 0){
            return true;
        }
        // 定义一个辅助栈，2个辅助指针
        Stack<Integer> stack = new Stack<>();
        int a1Index = 0;
        int a2Index = 0;
        while (a2Index <= a2.length-1){
            // 将a1数据全部压栈
            while (a1Index <= a1.length-1){
                if(stack.size() > 0){
                    if(stack.peek() != a2[a2Index]){
                        stack.push(a1[a1Index]);
                        a1Index++;
                    }else{
                        stack.pop();
                        a2Index++;
                    }
                }else{
                    stack.push(a1[a1Index]);
                    if(stack.peek() != a2[a2Index]){
                        a1Index++;
                    }else{
                        stack.pop();
                        a2Index++;
                    }
                }
            }
            if(stack.peek() != a2[a2Index]){
                return false;
            }else{
                stack.pop();
                a2Index++;
            }
        }

        return true;
    }

    public static boolean sulotion2(){
        return true;
    }
}
