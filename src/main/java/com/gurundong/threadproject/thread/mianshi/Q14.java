package com.gurundong.threadproject.thread.mianshi;
import java.util.Stack;

// 用两个栈实现一个队列。队列声明如下，请实现它的两个函数appendTail和deleteHead，分别完成在队列尾部插入节点和在队列头部删除节点功能
public class Q14 {
    public static Stack<Integer> stack1 = new Stack<>();
    public static Stack<Integer> stack2 = new Stack<>();

    public static void main(String[] args) {
        MyList.push(1);
        MyList.push(2);
        MyList.push(3);
        MyList.push(4);
        MyList.push(5);
        MyList.push(6);
        MyList.push(7);
        MyList.push(8);
        MyList.push(9);
        MyList.push(10);
        MyList.push(11);
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
        System.out.println(MyList.get());
    }

    static class MyList{
        public static Integer get(){
            if(stack2.isEmpty()){
                if(stack1.isEmpty()){
                    return null;
                }
                while (!stack1.isEmpty()){
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        public static void push(Integer data){
            if(stack1.size() >= 10){
                System.out.println("队列容量为10，添加失败");
                return;
            }
            stack1.push(data);
        }

        // 队列尾部插入节点
        public static void appendTail(Integer data){
            push(data);
        }

        // 队列头部删除节点
        public static void deleteHead(){
            get();
        }
    }
}
