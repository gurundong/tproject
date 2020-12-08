package com.gurundong.threadproject.thread.mianshi;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// https://blog.csdn.net/weixin_39485901/article/details/91493277
// 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，
// 他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
// {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
public class J65滑动窗口最大值 {
    public static void main(String[] args) {
        int[] input = {2,3,4,2,6,2,5,1};
        ArrayList<Integer> res = solution01(input,3);
        res.forEach(v -> System.out.print(v));
    }

    public static ArrayList<Integer> solution01(int[] num,int size){
        // 结果集，存储的是每一个滑动窗口中最大值的坐标
        ArrayList<Integer> list = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<>();
        // 判断异常情况
        if(num == null || size<=0 || size>num.length){
            return list;
        }
        // 开始滑动窗口
        for (int i = 0; i < num.length; i++) {
            // 如果当前遍历到的数大于双端队列的末尾值（下一个候选窗口最大值），就去除掉队列的末尾值
            while (!deque.isEmpty() && num[i] >= num[deque.peekLast()]){
                deque.removeLast();
            }
            // 若当前滑动窗口已滑出，则去掉队列首端的最大值，相当于是双向队列中的最大值坐标已经不在滑动窗口中了
            if(!deque.isEmpty() && i - deque.peekFirst() >= size){
                deque.removeFirst();
            }
            // 加入下一候选窗口值
            deque.add(i);
            // 滑动索引大于size开始，每滑动一次就添加一次当前窗口极大值
            if(i>=size-1){
                list.add(num[deque.peekFirst()]);
            }
        }
        return list;
    }
}
