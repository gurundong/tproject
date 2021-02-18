package com.gurundong.threadproject.thread.mianshi.链表;

import com.gurundong.threadproject.thread.mianshi.链表.SingleLink;

// 链表倒数第k个节点，输入一个链表，输出该链表的倒树n个节点。 输入1,2,3,4,5  取倒数3个节点，则为3,4,5
public class 输出该链表的倒树n个节点 {
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

        SingleLink.Node node = sulotion1(node1,5);
        System.out.println(node.data);
    }

    // 返回链表中倒数第K个结点
    public static SingleLink.Node sulotion1(SingleLink.Node head,int k){
        // 第一个索引
        int index1 = 0;
        // 第二个索引
        int index2 = index1 + k;

        // 第一个指针的节点
        SingleLink.Node node1 = head;

        // 第二个指针的节点
        SingleLink.Node node2 = head;
        for (int i = 1; i < k; i++) {
            // 说明节点长度不够，长度还不够k
            if(node2.next == null){
                return null;
            }
            node2 = node2.next;
        }

        while (node2.next != null){
            node1 = node1.next;
            node2 = node2.next;
        }

        return node1;
    }
}
