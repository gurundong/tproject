package com.gurundong.threadproject.thread.mianshi.链表;

import java.util.ArrayList;
import java.util.Stack;

public class 反转链表 {
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
        System.out.println("solution1");
        singleLink.showList();
        linkReverse(singleLink.head);
        singleLink.showList();
    }


    // 链表反转的方法
    public static SingleLink.Node linkReverse(SingleLink.Node head){
        SingleLink.Node tmp = head.next;
        SingleLink.Node tmp2 = tmp.next;
        SingleLink.Node tmp3 = tmp2.next;
        tmp.next = null;
        while(tmp3.next != null){
            tmp2.next = tmp;
            tmp = tmp2;
            tmp2 = tmp3;
            tmp3 = tmp3.next;
        }
        tmp2.next = tmp;
        tmp3.next = tmp2;
        head.next = tmp3;
        return head;
    }
}
