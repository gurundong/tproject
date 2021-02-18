package com.gurundong.threadproject.thread.mianshi.栈;

import java.util.Stack;

// push、pop、min都必须O(1)复杂度
// 待优化技巧：设置辅助栈，存最小值的下标
public class J21包含min函数的栈 {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public void push(int x){
        stack1.push(x);
        // 如果为空，或者辅助栈的顶部元素比待插入元素大，则说明待插入元素的当前节点及之前节点的最小值，则压入x
        if(stack2.isEmpty() || stack2.peek()>x)
            stack2.push(x);
        // 如果辅助栈的顶部元素比待插入元素小，则说明栈顶元素的当前节点及之前节点的最小
        else
            stack2.push(stack2.peek());
    }

    public void pop(){
        stack1.pop();
        stack2.pop();
    }

    public int getMin(){
        return stack2.peek();
    }
}
