package com.gurundong.threadproject.thread.mianshi.树;

// 思路通过临时变量交换左右节点，并递归遍历处理子节点
public class J19二叉树的镜像 {
    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public static BinaryTreeNode solution(BinaryTreeNode root){
        convert(root);
        return root;
    }

    public static void convert(BinaryTreeNode node){
        if(node != null){
            BinaryTreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            // 左递归
            convert(node.left);
            // 右递归
            convert(node.right);
        }
    }
}
