package com.gurundong.threadproject.thread.mianshi.搜索算法;

/**
 * 1. 首先确认该数组的中间下标：mid = (left+right)/2
 * 2. 让需要查找的数findVal与arr[mid]比较
 * 2.1 findVal>arr[mid],说明查找的数在mid的右边,需要递归向右查找
 * 2.2 findVal<arr[mid],说明查找的数在mid的左边,需要递归向左查找
 * 2.3 findVal=arr[mid],已找到
 *
 * 何时结束递归：
 * 1. 找到数值后就返回
 * 2. 递归完整个数组，仍然没有找到findVal,也需要结束递归 当left>right就需要退出
 */
public class 二分查找 {
    public static void main(String args[]){
        int[] a = {1,3,3,5,7,9,11,14,16,17};
        int index = binarySearch(a,0,a.length-1,3);
        // 数组下标为0开始，所以最后的位置可以+1更可读一些
        System.out.println(index+1);
    }

    // 返回查找到的位置索引
    public static int binarySearch(int[] arr,int left,int right,int findVal){
        if(left>right){
            return -1;
        }
        int mid = (left+right)/2;
        int midVal = arr[mid];
        if(midVal<findVal){
            return binarySearch(arr,mid+1,right,findVal);
        }else if(midVal>findVal){
            return binarySearch(arr,left,mid-1,findVal);
        }else{
            return mid;
        }
    }
}
