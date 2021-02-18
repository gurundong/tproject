package com.gurundong.threadproject.thread.mianshi;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个数组合并
 */
public class 两个有序数组合并 {
    public static void main(String[] args){
        int[] a = {1,2,7};
        int[] b = {3,4,8};
        f(a,b);
    }

    public static void f(int[] a,int[] b){
        List<Integer> res = new ArrayList<>(a.length+b.length);
        int A1 = 0;
        int B1 = 0;
        while (A1 < a.length){
            if(a[A1] < b[B1]){
                res.add(a[A1]);
                A1++;
            }
            else{
                res.add(b[B1]);
                B1++;
            }
        }
        for (int j = B1; j < b.length; j++) {
            res.add(b[B1]);
        }
        System.out.println(res);
    }
}
