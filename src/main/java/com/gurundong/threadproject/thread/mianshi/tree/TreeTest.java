package com.gurundong.threadproject.thread.mianshi.tree;

public class TreeTest{
    public static void main(String args[]){
        TreeNode root = new TreeNode(1,"1111");
        TreeNode node2 = new TreeNode(2,"2222");
        TreeNode node3 = new TreeNode(3,"3333");
        TreeNode node4 = new TreeNode(4,"4444");
        TreeNode node5 = new TreeNode(5,"5555");
        TreeNode node6 = new TreeNode(6,"6666");
        Tree tree = new Tree();
        tree.setRoot(root);
        root.setLeft(node2);
        node2.setLeft(node4);
        root.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node6);
        System.out.println("前序");
        tree.front();
        System.out.println("中序");
        tree.middle();
        System.out.println("后序");
        tree.backend();
    }
}

class Tree {
    private TreeNode root;

    // 前序遍历
    public void front(){
        if(this.root == null){
            System.out.println("无root节点，无法遍历");
        }
        this.root.front();
    }

    // 中序遍历
    public void middle(){
        if(this.root == null){
            System.out.println("无root节点，无法遍历");
        }
        this.root.middle();
    }

    // 后序遍历
    public void backend(){
        if(this.root == null){
            System.out.println("无root节点，无法遍历");
        }
        this.root.backend();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
}

class TreeNode {
    private int no;
    private String name;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    // 前序遍历
    public void front(){
        System.out.println(this);
        if(this.left != null){
            this.left.front();
        }
        if(this.right != null){
            this.right.front();
        }
    }

    // 中序遍历
    public void middle(){
        if(this.left != null){
            this.left.middle();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.middle();
        }
    }

    // 后序遍历
    public void backend(){
        if(this.left != null){
            this.left.backend();
        }
        if(this.right != null){
            this.right.backend();
        }
        System.out.println(this);
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
