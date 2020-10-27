package com.gurundong.threadproject.thread.mianshi;

import java.util.Arrays;

/**
    双点思路。
    1. 同向双点
    2. 异向双点

 */
public class Q6 {
    public static void main(String[] args) {
        char[] in = {'h','e','l','l','o'};
        char[] out = test01(in);
        System.out.println(Arrays.toString(out));
        char[] in2 = {'h','e','l','l','o'};
        char[] out2 = test02(in2);
        System.out.println(Arrays.toString(out2));
    }

    /*
    字符串反转 输入：['h','e','l','l','o']
    输出： ['o','l','l','e','h']
     */
    public static char[] test01(char[] in){
        // 使用反双指针
        int j = in.length-1;
        char temp;
        for(int i = 0;i<(in.length/2);i++){
            temp = in[i];
            in[i] = in[j];
            in[j--] = temp;
        }
        return in;
    }

    /*
    待完善
    字符串移除重复元素 输入：['h','e','l','l','o','l']
    输出：['h','e','l','o']
     */
    public static char[] test02(char[] in){
        // 使用同向双指针
        int i = 0,j=0;
        while (j <= in.length){
            if(i == 0|| in[j] != in[i-1]){
                in[i++] = in[j++];
            }else {
                j++;
            }
        }
        char[] res = new char[i+1];
        for (int k = 0; k <= i; k++) {
            res[k] = in[k];
        }
        return res;
    }
}
