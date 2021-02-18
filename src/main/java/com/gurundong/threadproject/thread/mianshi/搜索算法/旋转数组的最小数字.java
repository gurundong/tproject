package com.gurundong.threadproject.thread.mianshi.搜索算法;

public class 旋转数组的最小数字 {

    public static void main(String[] args) {
        int[] data = {13,14,15,5,9};
        int res = solution01(data);
        System.out.println(res);
    }

    // 二分法
    public static int solution01(int[] data){
        if(data == null || data.length <= 0){
            return Integer.MIN_VALUE;
        }
        // 左指针
        int index1 = 0;
        // 右指针
        int index2 = data.length - 1;

        int indexMid = 0;
        while (data[index1] >= data[index2]){
            // 如果index1和index2指向相邻的两个数，
            // 则index1指向第一个递增子数组的最后一个数字，
            // index2指向第二个子数组的第一个数字，也就是数组中的最小数字4
            if (index2 - index1 == 1)
            {
                indexMid = index2;
                break;
            }

            indexMid = (index1 + index2) / 2;

            // 缩小查找范围
            // 如果中间值大于第一个值，说明此中间值在前面的递增队列，数组最小值还在后面
            if (data[indexMid] >= data[index1])
            {
                index1 = indexMid;
            }
            // 如果中间值小于最后一个值，说明此中间值在后面的递增队列，数组最小值还在前面
            else if (data[indexMid] <= data[index2])
            {
                index2 = indexMid;
            }
        }

        return data[indexMid];
    }
}
