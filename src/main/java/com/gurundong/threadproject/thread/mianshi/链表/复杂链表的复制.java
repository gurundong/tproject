package com.gurundong.threadproject.thread.mianshi.链表;

class Node {
    public Node(Object data) {
        this.data = data;
    }

    public Node next;  // 下一个节点
    public Node random;  // 随机节点
    public Object data; // 数据
}


// 思路： 先在原链表上复制，然后按照奇数和偶数将一个大的链表拆分成2个
public class 复杂链表的复制 {
    public static void main(String[] args) {
        Node a = new Node("a");
        Node b = new Node("b");
        Node c = new Node("c");
        Node d = new Node("d");
        Node e = new Node("e");
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        a.random = c;
        b.random = e;
        d.random = b;
        Node node = solution01(a);
        while (node.next != null) {
            System.out.println(node.data);
            node = node.next;
        }
        // 打印尾结点
        System.out.println(node.data);
    }

    // 第一步：将复杂链表的labe和next复制一份，就是复制节点A得到A1，将A1插入到A的后面
    // 第二步：同步复制出来的节点的random，（当前的random就是原来节点的random的下一个节点）
    // A1->random = A->random->next;
    // 第三步，按奇数与偶数拆分链表，重新连接
    public static Node solution01(Node pHead) {
        if (pHead == null) {
            return null;
        }
        // 指向当前节点
        Node pCur = pHead;
        // 第一步：复制
        while (pCur != null) {
            // 构造复杂结点信息
            Node temp = new Node(pCur.data + "'");
            temp.next = pCur.next;
            // 将temp结点插入A后面
            pCur.next = temp;
            // 自增
            pCur = temp.next;
        }
        // 第二步:设置random
        pCur = pHead;
        while (pCur != null) {
            // temp 指向A1
            Node temp = pCur.next;
            if (pCur.random != null) {
                //设置A1的random
                temp.random = pCur.random.next;
            }
            pCur = temp.next;  // 自增
        }
        // 第三步：此时复杂链表已经全部复制了，只需要将链表拆开（把A和A1分离）
        pCur = pHead;
        Node pClone = pHead.next;
        Node temp = null;
        // 不构造链表，不引入额外空间，直接更改指针指向，奇数指向奇数，偶数指向偶数。
        while (pCur.next != null) {
            if (pCur.next.next == null) {
                break;
            }
            temp = pCur.next;
            pCur.next = temp.next;
            if (temp.next != null) {
                temp.next = temp.next.next;
            }
            pCur = pCur.next;
        }
        return pClone;
    }
}
