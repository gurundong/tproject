package com.gurundong.threadproject.thread.mianshi;

import java.util.HashSet;
import java.util.Set;

public class Q9 {
    public static void main(String[] args) {
        String s = "1adbskdiabdlls";
        int n = test01(s);
        System.out.println(n);
    }

    // 最长无重复子串
    public static int test01(String s){
        int n = s.length();
        int res = 0;
        int end=0,start=0;
        Set<Character> set=new HashSet<>();
        while(start<n && end<n){
            if(set.contains(s.charAt(end))){
                set.remove(s.charAt(start++));
            }else{
                set.add(s.charAt(end++));
                res=Math.max(res,end-start);
            }

        }
        return res;

    }
}
