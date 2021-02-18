package com.gurundong.threadproject.thread.mianshi;

// 替换空格
// 请实现一个函数，将一个字符串中的每个空格替换成"%20"。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
public class 替换空格 {
    public static void main(String[] args) {
        System.out.println("replaceAll法:");
        System.out.println(solution2("We Are Happy"));
        System.out.println("最蠢方法，循环替换+拼接字符串法，空间复杂度太高:");
        System.out.println(solution1("We Are Happy"));
    }
    public static String solution1(String input){
        int length = input.length();
        int i = 0;
        while(i < length){
            if(input.charAt(i) == ' '){
                int j = i+1;
                while(input.charAt(j) == ' '){
                    j++;
                }
                String segment1 = input.substring(0,i);
                String segment2 = input.substring(j);
                // 将变量全部重新赋值，此时会有垃圾字符串产生，数量太大会撑爆内存
                input = segment1+"%20"+segment2;
                length = input.length();
                i = i+3;
            }else{
                i++;
            }
        }
        return input;
    }

    // 借助java的replaceAll方法，注意第一个参数为正则表达式
    public static String solution2(String input){
        return input.replaceAll(" ","%20");
    }
}
