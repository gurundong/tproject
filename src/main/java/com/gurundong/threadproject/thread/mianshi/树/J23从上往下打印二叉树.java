package com.gurundong.threadproject.thread.mianshi.树;

import java.util.LinkedList;
import java.util.Queue;

// 技巧： 不使用递归，构造队列与临时指针，不断用remove方法不断切换指针位置，从左到右，从上到下
public class J23从上往下打印二叉树 {
    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public static void solution(BinaryTreeNode root){
        if(root != null){
            // 存放还未遍历完的元素
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            // 根节点入队
            queue.add(root);
            // 指针，记录当前处理节点
            BinaryTreeNode cur;
            while (!queue.isEmpty()){
                // 删除队首元素，移动指针
                cur = queue.remove();
                // 输出队列首元素值
                System.out.println(cur.value + " ");
                // 左节点入队
                if(cur.left != null){
                    queue.add(cur.left);
                }
                // 右节点入队
                if(cur.right != null){
                    queue.add(cur.right);
                }
            }
        }
    }
}
