package com.gurundong.threadproject.thread.mianshi.链表;

// 合并两个排序的链表
public class 合并两个排序的链表 {


    // 归并排序
    public SingleLink.Node sulotion01(SingleLink.Node[] lists,int left, int right){
        if(left==right)return lists[left];
        //分左右两个ListNode
        SingleLink.Node leftNodes = sulotion01(lists, left, (right - left) / 2 + left);
        SingleLink.Node rightNodes = sulotion01(lists, (right - left) / 2 + left + 1, right);
        //合并左右两个ListNode
        if(leftNodes == null)return rightNodes;
        if(rightNodes == null)return leftNodes;
        SingleLink.Node newNode = null;
        if((Integer)leftNodes.data<(Integer)rightNodes.data){
            newNode = new SingleLink.Node((Integer)leftNodes.data);
            leftNodes = leftNodes.next;
        }else{
            newNode = new SingleLink.Node((Integer)rightNodes.data);
            rightNodes = rightNodes.next;
        }
        SingleLink.Node newHead = newNode;
        while(leftNodes!=null&&rightNodes!=null){
            if((Integer)leftNodes.data<(Integer) rightNodes.data){
                newHead.next=new SingleLink.Node(leftNodes.data);
                leftNodes = leftNodes.next;
            }else{
                newHead.next=new SingleLink.Node(rightNodes.data);
                rightNodes = rightNodes.next;
            }
            newHead = newHead.next;
        }
        if(leftNodes!=null){
            newHead.next = leftNodes;
        }
        if(rightNodes!=null){
            newHead.next = rightNodes;
        }
        return newNode;
    }
}
