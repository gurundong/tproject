package com.gurundong.threadproject.thread.mianshi.链表;

public class 两个链表的第一个公共结点 {
    public static void main(String[] args) {
        //构造链表结构测试用
        SingleLink.Node a = new SingleLink.Node(1);
        SingleLink.Node b = new SingleLink.Node(2);
        SingleLink.Node c = new SingleLink.Node(3);
        SingleLink.Node d = new SingleLink.Node(4);
        SingleLink.Node e = new SingleLink.Node(5);
        SingleLink.Node f = new SingleLink.Node(6);
        SingleLink.Node g = new SingleLink.Node(7);
        //第一个List
        a.next = b;
        b.next = c;
        c.next = f;
        f.next = g;
        //第二个List
        d.next = e;
        e.next = f;
        f.next = g;
        SingleLink.Node result =  solution01(a,d);
        System.out.println("相交节点："+result.data);
    }

    // 方案1，时间复杂度为O(m+n)，判断两个链表的长度，首先让长的链表先走几步，随后一起往后遍历，直到遇到相等的元素为止
    public static SingleLink.Node solution01(SingleLink.Node pHead1, SingleLink.Node pHead2) {
        // 链表1的长度
        int count1 = 0;
        // 链表2的长度
        int count2 = 0;
        // 公共节点
        SingleLink.Node commonNode = null;
        // 链表1指针
        SingleLink.Node pNode1 = pHead1;
        // 链表2指针
        SingleLink.Node pNode2 = pHead2;
        //得到链表1的长度
        while (pNode1 != null) {
            count1++;
            pNode1 = pNode1.next;
        }
        //得到链表2的长度
        System.out.println("List1的长度为：" + count1);
        while (pNode2 != null) {
            count2++;
            pNode2 = pNode2.next;
        }
        System.out.println("List1的长度为：" + count2);
        //令pNode1和pNode2重新指向头结点
        pNode1 = pHead1;
        pNode2 = pHead2;
        int sub = count1 - count2;
        System.out.println("两个List相差" + sub + "个节点");
        //先在长链表上走几步，再同时在两个链表上遍历
        if (sub > 0) {
            for (int i = 0; i < sub; i++) {
                pNode1 = pNode1.next;
            }
        } else {
            for (int i = 0; i < Math.abs(sub); i++) {
                pNode2 = pNode2.next;
            }
        }
        System.out.println("List1从" + pNode1.data + "开始比较，List2从" + pNode2.data + "开始比较");
        //得到第一个公共节点
        while (pNode1 != null && pNode2 != null) {
            if (pNode1 != pNode2) {
                pNode1 = pNode1.next;
                pNode2 = pNode2.next;
            } else {
                commonNode = pNode1;
                return commonNode;
            }
        }
        return null;

    }

    // 可使用倒遍历法，借助栈实现，暂不做实现
    public static SingleLink.Node solution02(){
        return null;
    }
}
