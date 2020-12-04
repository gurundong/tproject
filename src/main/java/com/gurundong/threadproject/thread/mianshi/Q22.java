package com.gurundong.threadproject.thread.mianshi;

import com.gurundong.threadproject.thread.mianshi.链表.SingleLink;

// 链表倒树第k个节点，输入一个链表，输出该链表的倒树n个节点。 输入1,2,3,4,5  取倒数3个节点，则为3,4,5
public class Q22 {
    public static void main(String[] args) {
        SingleLink singleLink = new SingleLink();
        SingleLink.Node node1 = new SingleLink.Node("1");
        SingleLink.Node node2 = new SingleLink.Node("2");
        SingleLink.Node node3 = new SingleLink.Node("3");
        SingleLink.Node node4 = new SingleLink.Node("4");
        SingleLink.Node node5 = new SingleLink.Node("5");
        singleLink.add(node1);
        singleLink.add(node2);
        singleLink.add(node3);
        singleLink.add(node4);
        singleLink.add(node5);
        singleLink.showList();
    }

    // 时间复杂度O(2n)法
    public static void sulotion1(SingleLink singleLink){
        // 先计算长度
        int length = 0;
        
    }

    // 时间复杂度O(n法)
    public static void sulotion2(SingleLink singleLink){
        //
    }
}
