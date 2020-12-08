package com.gurundong.threadproject.thread.mianshi.树;

// 输入一个整数数组，判断是否是二叉树的后序遍历序列
// 思路，依次判断各个子树的左子树是否都小于 根，右子树是否都大于 根，使用dfs法递归
public class J24二叉搜索树后续遍历序列 {
    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }


    public static boolean solution(int[] input){
        if(input == null || input.length <= 0){
            return false;
        }
        return dfs(input,0,input.length-1);
    }

    public static boolean dfs(int[] input,int start,int end){
        return false;
    }
}
