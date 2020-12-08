package com.gurundong.threadproject.thread.mianshi.树;

import java.util.ArrayList;
import java.util.List;

// 结合dfs+回溯解决
public class J25二叉树和为某一值的路径 {
    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public static void findPath(BinaryTreeNode root,int inputSum){
        List<Integer> list = new ArrayList<>();
        if(root != null){
            dfs(root,0,inputSum,list);
        }
    }

    public static void dfs(BinaryTreeNode node, int sum, int inputSum, List<Integer> result){
        if(node != null){
            sum = sum + node.value;
            result.add(node.value);
            if(sum < inputSum){
                // 递归左
                dfs(node.left,sum,inputSum,result);
                // 递归右
                dfs(node.right,sum,inputSum,result);
            }else if (sum == inputSum){
                if(node.left == null && node.right == null){
                    System.out.println(result);
                }
            }
            // 回溯
            result.remove(result.size());
        }
    }
}
