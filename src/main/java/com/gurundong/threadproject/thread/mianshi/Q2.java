package com.gurundong.threadproject.thread.mianshi;

// 青蛙跳台阶
public class Q2 {
    public static void main(String[] args) throws Exception {
        // 台阶数
        int n = 5;
        // 总共的可能跳法数量
        int num = f(5);
    }

    public static int f(int n) {
        if(n == 0){
            return 1;
        }
        if(n == 1){
            return 1;
        }
        return f(n-1) + f(n-2);
    }
}
