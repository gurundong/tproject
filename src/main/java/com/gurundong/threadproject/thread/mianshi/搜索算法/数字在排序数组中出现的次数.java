package com.gurundong.threadproject.thread.mianshi.搜索算法;

public class 数字在排序数组中出现的次数 {

    public static void main(String[] args) {
        int[] data = {1,2,2,2,2,2,2,3,4,5};
        int start = findFirstIndex(data,2,0,data.length-1);
        int end = findEndIndex(data,2,0, data.length-1);
        int length = end - start + 1;
        System.out.println("start : "+start+" end : "+end+" length："+length);
    }

    /**
     * 找到第一次出现的索引
     * @param data 数组
     * @param k 代找数
     * @param start 开始索引
     * @param end 结束索引
     * @return 返回开始索引
     */
    public static int findFirstIndex(int[] data,int k,int start,int end){
        if(start > end){
            return -1;
        }
        int middle = (start + end) / 2;
        if(data[middle] == k){
            // 如果是第一个元素，或者左边的元素不在是相同的元素，则这就是第一次出现的位置，返回即可
            if (middle == 0 || (middle > 0 && data[middle-1] != k)){
                return middle;
            }else {
                end = middle-1;
            }
        }else if(data[middle] < k){
            start = middle + 1;
        }else{
            end = middle - 1;
        }
        return findFirstIndex(data,k,start,end);
    }

    /**
     * 找到最后一次出现的索引
     * @param data 数组
     * @param k 代找数
     * @param start 开始索引
     * @param end 结束索引
     * @return 返回结束索引
     */
    public static int findEndIndex(int[] data,int k,int start,int end){
        if(start > end){
            return -1;
        }
        int middle = (start + end) / 2;
        if(data[middle] == k){
            // 如果是最后一个元素，或者右边的元素不在是相同的元素，则这就是最后一次出现的位置，返回即可
            if(middle == data.length -1 || (middle < data.length-1 && data[middle+1] != k)){
                return middle;
            }else {
                start = middle + 1;
            }
        }
        else if(data[middle] < k){
            start = middle + 1;
        }else {
            end = middle - 1;
        }
        return findEndIndex(data,k,start,end);
    }

}
