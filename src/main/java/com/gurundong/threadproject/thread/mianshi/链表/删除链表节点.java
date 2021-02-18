package com.gurundong.threadproject.thread.mianshi.链表;

import com.gurundong.threadproject.thread.mianshi.链表.SingleLink;

// 在O(1)时间删除链表节点
public class 删除链表节点 {

    public static void main(String[] args) {
        SingleLink singleLink = new SingleLink();
        SingleLink.Node node1 = new SingleLink.Node("1");
        SingleLink.Node node2 = new SingleLink.Node("2");
        SingleLink.Node node3 = new SingleLink.Node("3");
        SingleLink.Node node4 = new SingleLink.Node("4");
        SingleLink.Node node5 = new SingleLink.Node("5");
        SingleLink.Node node6 = new SingleLink.Node("6");
        singleLink.add(node1);
        singleLink.add(node2);
        singleLink.add(node3);
        singleLink.add(node4);
        singleLink.add(node5);
        showList(singleLink);
        // 删除第一个节点测试
        System.out.println("删除第一个节点测试");
        singleLink.head.next = deleteNodeO1(singleLink.head.next,node1);
        showList(singleLink);
        // 删除第二个节点测试
//        System.out.println("删除第二个节点测试");
//        singleLink.head.next = deleteNodeO1(singleLink.head.next,node2);
//        showList(singleLink);
//        // 删除最后一个节点测试
//        System.out.println("删除最后一个节点测试");
//        singleLink.head.next = deleteNodeO1(singleLink.head.next,node5);
//        showList(singleLink);
        // 删除无效节点测试
//        System.out.println("删除无效节点测试");
//        singleLink.head.next = deleteNodeO1(singleLink.head.next,node6);
//        showList(singleLink);
    }

    /**
     * O(1)删除节点
     * @param head 非空头节点指针
     * @param node 待删除节点指针
     */
    // 给定head节点指针与任意节点指针，判断指针地址相等用 ==
    public static SingleLink.Node deleteNodeO1(SingleLink.Node head,SingleLink.Node node){
        // 如果只有一个节点
        if(head.next == null && head == node){
            head = null;
        }
        // 如果删除的是最后一个节点
        else if(node.next == null){
            SingleLink.Node tmp = head;
            while (tmp.next != null){
                if(tmp.next == node){
                    tmp.next = null;
                    break;
                }
                tmp = tmp.next;
            }
        }
        // 如果删除的是中间节点
        else{
            SingleLink.Node tmp = head;
            // 删除第一个节点
            if(tmp == node){
                head = head.next;
            }else{
                while (tmp.next != null){
                    // 此种情况会出现bug，第一个节点删不了，因为第一次判断就是tmp.next
                    if(tmp.next == node){
                        tmp.next = node.next;
                        node.next = null;
                        break;
                    }
                    tmp = tmp.next;
                }
            }
        }
        return head;
    }

    public static void showList(SingleLink singleLink){
        SingleLink.Node tmp = singleLink.head.next;
        while (tmp.next != null){
            System.out.print(tmp.data);
            System.out.print("->");
            tmp = tmp.next;
        }
        System.out.print(tmp.data);
        System.out.println();
    }
}
