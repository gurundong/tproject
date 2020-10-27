package com.gurundong.threadproject.thread.mianshi;

import java.util.Arrays;

/*
 dfs问题
 */
public class Q8 {
    public static void main(String[] args) {
        char[][] n = {
                {'1','0','1','0','0','0'},
                {'1','0','1','0','0','1'},
                {'0','1','1','1','1','0'},
                {'0','0','1','0','0','0'}
        };
        System.out.println(Arrays.toString(n));
        int num = test01(n);
        System.out.println("数量："+num);
    }

    /*
    获取岛屿的数量
     */
    public static int test01(char[][] n){
        int num = 0;
        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                if(n[i][j] == '1'){
                    num++;
                    test01Dfs(n,i,j);
                }
            }
        }
        return num;
    }

    // 访问矩阵中的每一个点，每执行一套，就是一个岛屿
    public static void test01Dfs(char[][] n,int i,int j){
        // 当出界，就退出
        if(!isValid(i,j,n)){
            return;
        }
        // 如果访问的不是没有访问过的陆地，就退出
        if(n[i][j] != '1'){
            return;
        }
        // 触发preOrder，把该点置为2，表示已访问过
        n[i][j] = 2;
        // 访问左
        test01Dfs(n,i-1,j);
        // 访问右
        test01Dfs(n,i+1,j);
        // 访问上
        test01Dfs(n,i,j+1);
        // 访问下
        test01Dfs(n,i,j-1);
    }

    /*
    返回true代表是陆地
     */
    public static boolean isValid(int i,int j,char[][] n){
        int xCount = n.length;
        int yCount = n[0].length;
        if(i < 0 || i > xCount-1 || j < 0 || j > yCount -1){
            return false;
        }
        return true;
    }
}
