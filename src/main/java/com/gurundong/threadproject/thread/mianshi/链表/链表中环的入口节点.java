package com.gurundong.threadproject.thread.mianshi.链表;

public class 链表中环的入口节点 {
    /**
     * 如何判断链表有环？
     *  若快慢指针相遇，则存在环，若fast指向NULL，则不存在环
     * 如何找到环的入口结点？
     *  直接记住结论，在问题一中两指针相遇后，让一个指针从头结点开始（记快指针），另一个从相遇结点开始，并以相同速度向后指，再次相遇时就是环的入口结点。
     * @return
     */
    public static SingleLink.Node solution01(SingleLink.Node pHead){
        SingleLink.Node fast = pHead;
        SingleLink.Node slow = pHead;
        while(fast != null && fast.next != null){
            // 快指针
            fast = fast.next.next;
            // 慢指针
            slow = slow.next;
            //当快指针 与 慢指针相遇时
            if(fast == slow){
                // 快指针重新指向链表头
                fast = pHead;
                //再次相遇
                while(fast != slow){
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }
}
