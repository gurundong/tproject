package com.gurundong.threadproject.thread.mianshi.数组;

// 交换数组中元素位置，使得奇数全部在左边，偶数全部在右边
public class J14调整数组奇数偶数的位置 {
    public static void solution(int[] in){
        // 判断边界条件
        if(in == null || in.length < 2){
            return;
        }
        // 左指针位置
        int left = 0;
        // 右指针位置
        int right = in.length - 1;
        // 遍历数组调整奇数偶数的位置
        while(left < right){
            // 从左到右找偶数
            while (left < right && in[left] % 2 != 0){
                left++;
            }
            // 从右到左找奇数
            while(left < right && in[right] % 2 == 0){
                right--;
            }
            // left与right相等不会产生其它影像，可以省略if判断
            int tmp = in[left];
            in[left] = in[right];
            in[right] = tmp;
        }
    }
}
