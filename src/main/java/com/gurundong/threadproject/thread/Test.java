package com.gurundong.threadproject.thread;

import java.util.Scanner;

public class Test {

    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public static void solution01(BinaryTreeNode root){
        if(root != null){
            BinaryTreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;

            solution01(root.left);
            solution01(root.right);
        }
    }
}
