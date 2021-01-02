package com.gurundong.threadproject.thread.common.utils;

public class RandomUtil {
    //获取[0,n)之间的一个随机整数
    public static int getRandomInt(int n) {
        return (int) (Math.random() * n);
    }

    //获取[0,n)之间的一个随机浮点数
    public static double getRandomFloat(int n) {
        return (Math.random() * n);
    }


    //获取[m,n]之间的随机数（0<=m<=n）
    public static int getRandomBetweenInt(int m,int n){
        return (int)(m + Math.random() * (n - m + 1));
    }

    //获取[m,n]之间的随机数（0<=m<=n）
    public static float getRandomBetweenFloat(int m,int n){
        return (float)(m + Math.random() * (n - m + 1));
    }
}
