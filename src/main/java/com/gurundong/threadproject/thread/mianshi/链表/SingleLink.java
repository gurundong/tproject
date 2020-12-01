package com.gurundong.threadproject.thread.mianshi.链表;

public class SingleLink {
    // 有头节点
    public Node head = new Node(null);

    public static class Node {
        public Node next;  // 下一个节点
        public Object data; // 数据

        public Node(Object data) {
            this.data = data;
        }

        public void addNode(Node newNode){
            // 递归到最后一层，将新节点添加到尾部
            if(this.next == null){
                this.next = newNode;
            }else{
                this.next.addNode(newNode); // 递归到下一个节点
            }
        }

        public boolean existNode(Node node){
            if(this.data == node.data){
                return true;
            }
            Node tmp = this;
            while (tmp.next != null){
                tmp = tmp.next;
                if(tmp.data == node.data){
                    return true;
                }
            }
            return false;
        }

        // 按照data域判断是否等
        public void showList(){
            System.out.println(this.data);
            Node tmp = this;
            while (tmp.next != null){
                tmp = tmp.next;
                System.out.println(tmp.data);
            }
        }
    }

    public void add(Node node){
        head.addNode(node);
    }

    // 按照data域是否相等判断删除
    public void delete(Node node){
        if(!head.existNode(node)){
            return;
        }
        // 先处理第一个节点
        if(head.next.data == node.data){
            head.next = head.next.next;
            return;
        }
        // 批量处理后面的节点
        Node pre = head.next;
        Node next;
        while (pre.next != null){
            next = pre.next;
            if(next.data == node.data){
                pre.next = next.next;
                return;
            }
            pre = pre.next;
        }
    }

    // 按照data域判断是否等
    public void show(){
        head.showList();
    }

    public boolean exist(Node node){
        return head.existNode(node);
    }


    public static void main(String[] args) {
        SingleLink singleLink = new SingleLink();
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        singleLink.add(node1);
        singleLink.add(node2);
        singleLink.add(node3);
        singleLink.add(node4);
        singleLink.add(node5);
        System.out.println("添加1，2，3，4，5");
        singleLink.show();
        System.out.println("测试exist函数");
        System.out.println(singleLink.exist(node2));
        Node node6 = new Node("6");
        System.out.println(singleLink.exist(node6));
        singleLink.delete(node2);
        System.out.println("测试delete函数");
        singleLink.show();
    }
}
