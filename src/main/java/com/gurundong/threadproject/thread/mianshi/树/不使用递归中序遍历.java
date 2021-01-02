package com.gurundong.threadproject.thread.mianshi.树;

import org.springframework.util.StringUtils;

import java.util.*;

//              1
//           /     \
//          2       3
//         /       / \
//        4       5   6
//         \         /
//          7       8
// 4,7,2,1,5,3,8,6
// https://www.cs.usfca.edu/~galles/visualization/Algorithms.html
// 使用队列、栈解决
public class 不使用递归中序遍历 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int x){
            this.val = x;
        }
    }

    // 使用栈模拟递归，此种方式改变了二叉树结构
    public static List<Integer> midOrder2(TreeNode node){
        // 返回容器
        List<Integer> res = new ArrayList<>();
        // 边界条件判断
        if(node == null){
            return res;
        }
        // 临时指针变量
        TreeNode cur;
        // 临时栈，用于模拟递归栈
        Stack<TreeNode> stack = new Stack<>();
        // 添加根节点
        stack.push(node);
        while (stack.size() > 0){
            // 获取栈顶节点，判断是否需要继续向下递归
            cur = stack.peek();
            // 处理异常null值
            if(cur == null){
                stack.pop();
                continue;
            }
            // 由于是中序遍历，所以继续遍历左节点
            if(cur.left != null){
                stack.push(cur.left);
                // 此步骤非常重要，意思是递归出栈时，避免相同的left节点重复入栈
                cur.left = null;
                continue;
            }
            // 将遍历结果加入res
            res.add(cur.val);
            stack.pop();
            // 继续遍历右节点
            if(cur.right != null){
                stack.push(cur.right);
                cur.right = null;
                continue;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        node1.left = node2;
        node2.left = node4;
        node4.right = node7;
        node1.right = node3;
        node3.left = node5;
        node3.right = node6;
        node6.left = node8;
        List<Integer> list = midOrder2(node1);
        System.out.println(list);

//        midOrderRe(node1);
    }

    public static void midOrderRe(TreeNode biTree)
    {//中序遍历递归实现
        if(biTree == null)
            return;
        else
        {
            midOrderRe(biTree.left);
            System.out.println(biTree.val);
            midOrderRe(biTree.right);
        }
    }
}
