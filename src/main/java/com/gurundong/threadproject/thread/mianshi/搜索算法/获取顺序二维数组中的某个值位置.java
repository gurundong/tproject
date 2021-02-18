package com.gurundong.threadproject.thread.mianshi.搜索算法;

import java.util.Arrays;

// 获取顺序二维数组中的某个值位置
// 题目：在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
// 解法：dfs或while循环
public class 获取顺序二维数组中的某个值位置 {
    public static void main(String[] args) {
        int[][] data = {
                {1, 3, 7, 11},
                {2, 4, 12, 18},
                {6, 5, 13, 19},
                {8, 9, 17, 21},
        };
        for (int[] v: data) {
            System.out.println(Arrays.toString(v));
        }

        int input = 12;
        System.out.println("while循环法：");
        System.out.println(getDataIndex(data,input));
        System.out.println("dfs法");
        System.out.println(getDataIndex2(data,12,0,data[0].length-1));
    }

    // while循环法，时间复杂度也为2n
    public static String getDataIndex(int[][] data, int input) {
        int row = data.length-1;
        int col = data[0].length-1;
        // 从坐下角开始判断，此时j代表第一列
        int j = 0;
        while(row >= 0 && j <= col){
            if(input < data[row][j]){
                row--;
            }else if(input > data[row][j]){
                j++;
            }else{
                return "第"+(row+1)+"行,第" + (j+1) +"列";
            }
        }
        return "未找到";
    }

    // dfs法,时间复杂度为2n
    public static String getDataIndex2(int[][] data, int input,int i,int j) {
        if(j<0 || i >= data.length){
            return "未找到";
        }
        if (data[i][j] == input) {
            return "第"+(i+1)+"行,第" + (j+1) +"列";
        }
        if (data[i][j] < input) {
            i++;
            return getDataIndex2(data,input,i,j);
        }
        if (data[i][j] > input) {
            j--;
            return getDataIndex2(data,input,i,j);
        }
        return "未找到";
    }
}
