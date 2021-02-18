package com.gurundong.threadproject.thread.mianshi;

import java.util.*;

// 扑克牌中的顺子
public class 扑克牌中的顺子 {
    public static void main(String[] args){
        Integer[] a = {4,2,0,3,5};
        Boolean res = f(a);
        System.out.println(res);
    }

    public static Boolean f(Integer[] nums){
        Arrays.sort(nums);
        List<Integer> pai = new ArrayList<>();
        pai.addAll(Arrays.asList(nums));
        for (int i = 0; i < pai.size(); i++) {
            if(pai.get(i) == 0){
                pai.remove(i);
            }
        }
        for (int i = 1; i < pai.size(); i++) {
            if(pai.get(i-1) + 1 != pai.get(i)){
                return false;
            }
        }


        return true;
    }
}
