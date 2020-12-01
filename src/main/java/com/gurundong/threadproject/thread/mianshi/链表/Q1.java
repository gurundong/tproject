package com.gurundong.threadproject.thread.mianshi.链表;

// 获取链表中间元素，如果中间有两个元素，则取后一个
public class Q1 {
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
        System.out.println(getMiddleNode(singleLink));
    }

    // 双指针法获取链表的中间元素，时间复杂度为n/2
    public static int getMiddleNode(SingleLink singleLink){
        SingleLink.Node slow = singleLink.head.next;
        SingleLink.Node quick = singleLink.head.next;
        // 如果只有1个，则中间节点为0
        if(quick.next == null){
            return 0;
        }
        // 如果只有2个，则中间节点为1
        if(quick.next.next == null){
            return 1;
        }
        // 慢指针移动的次数
        int i = 0;
        // 此处是一个极易空指针的位置，只有当快指针quick的下一个或者下下个都不为空的时候，快指针才会继续往后移动
        while (quick.next != null && quick.next.next != null){
            slow = slow.next;
            quick = quick.next.next;
            i++;
        }
        if(quick.next == null){
            return i;
        }
        if(quick.next != null && quick.next.next == null){
            return i+1;
        }
        return 0;
    }
}


