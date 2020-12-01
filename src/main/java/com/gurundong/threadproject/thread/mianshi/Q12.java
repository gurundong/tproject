package com.gurundong.threadproject.thread.mianshi;

import com.gurundong.threadproject.thread.mianshi.链表.SingleLink;

import java.util.ArrayList;
import java.util.Stack;

// 从尾到头打印链表
// 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
// 最佳答案为solution3
public class Q12 {
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
        solution1(singleLink);


        SingleLink singleLink2 = new SingleLink();
        SingleLink.Node node1_2 = new SingleLink.Node("1");
        SingleLink.Node node2_2 = new SingleLink.Node("2");
        SingleLink.Node node3_2 = new SingleLink.Node("3");
        SingleLink.Node node4_2 = new SingleLink.Node("4");
        SingleLink.Node node5_2 = new SingleLink.Node("5");
        singleLink2.add(node1_2);
        singleLink2.add(node2_2);
        singleLink2.add(node3_2);
        singleLink2.add(node4_2);
        singleLink2.add(node5_2);
        System.out.println("solution2");
        solution2(singleLink2);


        SingleLink singleLink3 = new SingleLink();
        SingleLink.Node node1_3 = new SingleLink.Node("1");
        SingleLink.Node node2_3 = new SingleLink.Node("2");
        SingleLink.Node node3_3 = new SingleLink.Node("3");
        SingleLink.Node node4_3 = new SingleLink.Node("4");
        SingleLink.Node node5_3 = new SingleLink.Node("5");
        singleLink3.add(node1_3);
        singleLink3.add(node2_3);
        singleLink3.add(node3_3);
        singleLink3.add(node4_3);
        singleLink3.add(node5_3);
        System.out.println("solution3");
        solution3(singleLink3.head);
    }

    // 先获取链表长度，时间复杂度为O(n),再从后到前获取链表倒树的每一个元素，时间复杂度为O(n*n)
    // 此种太蠢
    public static void solution1(SingleLink singleLink){
        SingleLink.Node h = singleLink.head;
        int length = getLinkLength(h);
        ArrayList<SingleLink.Node> arrayList = new ArrayList<>();
        while (length > 0){
            arrayList.add(getLinkByIndex(h,length-1));
            length--;
        }
        System.out.println(arrayList);
    }

    // 获取链表长度
    public static int getLinkLength(SingleLink.Node head){
        int length = 1;
        SingleLink.Node tmp;
        // 有头结点
        tmp = head.next;
        while (tmp.next != null){
            tmp = tmp.next;
            length++;
        }
        return length;
    }

    // 获取链表某个索引位置的节点
    public static SingleLink.Node getLinkByIndex(SingleLink.Node head,int index){
        if (index < 0){
            return null;
        }
        if(index == 0){
            return head.next;
        }
        SingleLink.Node tmp;
        tmp = head.next;
        int i = 0;
        while (tmp.next != null){
            i++;
            tmp = tmp.next;
            if(i ==  index){
                return tmp;
            }
        }
        return null;
    }

    // 先链表反转、再输出，此种时间复杂度为O(2n)
    // 各人感觉此种方法效率最高，因为空间复杂度为O(n)
    public static void solution2(SingleLink singleLink){
        SingleLink.Node reverseLink = linkReverse(singleLink.head);
        SingleLink.Node tmp = reverseLink.next;
        ArrayList<SingleLink.Node> arrayList = new ArrayList<>();
        // 加入第一个节点
        arrayList.add(tmp);
        while (tmp.next != null){
            // 依次加入后面的节点
            tmp = tmp.next;
            arrayList.add(tmp);
        }
        System.out.println(arrayList);
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

    // 使用栈暂存的方式，栈代表着倒序
    // 此种方法最容易理解，时间复杂度为O(n)，但是空间复杂为O(2n)，引入了其它数据结构，空间复杂度略高
    public static void solution3(SingleLink.Node head){
        ArrayList<String> arrayList = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        if(head.next == null){
            System.out.println(arrayList);
            return;
        }
        SingleLink.Node tmp = head.next;
        stack.push((String) tmp.data);
        while (tmp.next != null){
            tmp = tmp.next;
            stack.push((String) tmp.data);
        }
        while (!stack.isEmpty()){
            arrayList.add(stack.pop());
        }
        System.out.println(arrayList);
    }
}
