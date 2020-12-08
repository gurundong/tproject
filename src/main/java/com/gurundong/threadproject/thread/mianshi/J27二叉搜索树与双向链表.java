package com.gurundong.threadproject.thread.mianshi;

// https://www.cnblogs.com/dazhu123/p/12552985.html
public class J27二叉搜索树与双向链表 {
    public static class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    // 移动的指针，指向遍历当前节点
    private static BinaryTreeNode preNode;
    // 新双向链表的头
    private static BinaryTreeNode newHead;

    // 思路一： 利用两个指针+中序遍历完成
    public static BinaryTreeNode solution1(BinaryTreeNode root){
        // 边界条件
        if(root == null){
            return null;
        }
        middleOrder(root);
        // 处理后的双向链表首尾相连
        newHead.left = preNode;
        preNode.right = newHead;
        return newHead;
    }

    // 中序遍历
    private static void middleOrder(BinaryTreeNode root) {
        if(root == null){
            return;
        }
        //中序遍历,
        middleOrder(root.left);
        //中序遍历，第一个遍历的节点就是最左下的最小节点。
        //即第一个访问的节点就为左下角节点。
        //如果出preNode，则指向此时root，即为最左下节点。用来第一次遍历时，初始化
        //第一次进入这里是最左子节点时，初始化所需节点指引。
        if(preNode == null){
            newHead = root;
            preNode = root;
        }//否则，
        else{
            //以preNode始终指向前一个节点，正好是比当前节点小的节点，
            //进行以下操作，将其转成双向链表。
            preNode.right = root;
            root.left = preNode;
            preNode = root;
        }
        middleOrder(root.right);
    }

    // 思路二：通过额外引入栈或队列存储中序遍历的节点，再反演出来双向队列
    private static void solution2(){

    }
}
