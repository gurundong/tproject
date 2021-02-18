package com.gurundong.threadproject.thread.mianshi;

import java.util.HashMap;
import java.util.Map;

class DLinkedNode {

    String key;

    String value;

    DLinkedNode pre;

    DLinkedNode post;

}

public class LRUHashMap {

    // 用hashMap实现get\put 都是O(1) 复杂度
    private Map<String,DLinkedNode> map;

    // 总容量
    private int capacity;

    // 当前双向链表中的元素数
    private int count;

    // 头节点，尾节点
    private DLinkedNode head, tail;

    public LRUHashMap(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new DLinkedNode();
        head.pre = null;
        tail = new DLinkedNode();
        tail.post = null;
        // 初始化，首尾相连
        head.post = tail;
        tail.pre = head;
    }

    // 日常访问数据，如果有则更新到链表头部
    public String get(String key){
        DLinkedNode node = map.get(key);
        if(node == null){
            return null;
        }
        this.removeNode(node);
        this.addNode(node);
        return node.value;
    }

    // 增加数据
    public void set(String key, String value) {
        DLinkedNode node = map.get(key);
        if(node == null){
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;
            this.map.put(key,newNode);
            this.addNode(newNode);
            ++count;
            if(count > capacity){
                DLinkedNode dLinkedNode = this.popTail();
                this.map.remove(dLinkedNode.key);
                --count;
            }
        }else{
            node.value = value;
            this.removeNode(node);
            this.addNode(node);
        }

    }

    // 在双向链表中移除后一个已经存在的节点
    public void removeNode(DLinkedNode node){
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.post;
        // 前一个与后一个相连
        pre.post = post;
        post.pre = pre;
    }

    // 在第一个节点与最后一个节点之间插入一个接节点
    private void addNode(DLinkedNode node){
        node.pre = head;
        node.post = head.post;
        head.post.pre = node;
        head.post = node;
    }

    // 弹出双向链表尾部的节点
    private DLinkedNode popTail(){
        DLinkedNode res = tail.pre;
        this.removeNode(res);
        return res;
    }
}
