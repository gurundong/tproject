package com.gurundong.threadproject.thread.mianshi.链表;

public class 删除链表中重复的结点 {
    public static SingleLink.Node solution01(SingleLink.Node pHead) {
        //非递归解法
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        //初始化一个0为头结点，防止pHead的第一个结点和第二个结点相同的情况
        SingleLink.Node head = new SingleLink.Node(0);
        head.next = pHead;
        //pre指向确定不重复的结点
        SingleLink.Node pre = head;
        SingleLink.Node last = head.next;
        while (last != null) {
            //如果last和后面的指针相同
            if (last.next != null && last.data == last.next.data) {
                //找到last往后不重复的结点的位置
                while (last.next != null && last.data == last.next.data) {
                    last = last.next;
                }
                //相当于删除重复的结点
                pre.next = last.next;
                last = last.next;
            } else {
                //如果不重复，pre和last分别往后移动
                pre = pre.next;
                last = last.next;
            }
        }
        return head.next;
    }
}
