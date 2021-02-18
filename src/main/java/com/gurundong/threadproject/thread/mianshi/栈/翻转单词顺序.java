package com.gurundong.threadproject.thread.mianshi.栈;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// 1. 辅助栈法
// 2. 双指针法
public class 翻转单词顺序 {

    public static void main(String[] args) {
        String s2 = "I am a superman";
        System.out.println(solution01(s2.toCharArray()));
        char[] chars = solution02(s2.toCharArray());
        System.out.println(String.valueOf(chars));
    }

    // 使用两个辅助栈实现
    public static String solution01(char[] data){
        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();

        for (int i = 0; i < data.length; i++) {
            stack1.push(data[i]);
        }

//        List<Character> res = new ArrayList<>(data.length);
        StringBuilder res = new StringBuilder();

        while (!stack1.isEmpty()){
            if(stack1.peek() != ' '){
                stack2.push(stack1.pop());
            }else {
                while (!stack2.isEmpty()){
                    res.append(stack2.pop());
                }
                // 将空格直接加入结果集
                res.append(stack1.pop());
            }
        }
        while (!stack2.isEmpty()){
            res.append(stack2.pop());
        }
        return res.toString();
    }


    // 将data中start到end之间的数字反转
    public static void reverse(char[] data, int start, int end) {
        if (data == null || data.length < 1 || start < 0 || end > data.length || start > end) {
            return;
        }

        while (start < end) {
            char tmp = data[start];
            data[start] = data[end];
            data[end] = tmp;

            start++;
            end--;
        }
    }

    public static char[] solution02(char[] data){
        if (data == null || data.length < 1) {
            return data;
        }

        // 首先将所有的字符翻转
        reverse(data, 0, data.length - 1);

        // 定义两个指针，用来卡住一个单词，做翻转用
        int start = 0;
        int end = 0;

        while (start < data.length) {
            // 寻找第一个不是空格的指针位置，将左指针卡到单词的最左端
            if (data[start] == ' ') {
                start++;
                end++;
            }
            // 左右都卡主了，再将中间的单词翻转回正，最后将左、右指针都移动到下一个位置
            else if (end == data.length || data[end] == ' ') {
                reverse(data, start, end - 1);
                end++;
                start = end;
            }
            // 寻找该单词最后一个不是空格的指针位置，将右指针卡到单词的最右端
            else {
                end++;
            }
        }

        return data;
    }
}
