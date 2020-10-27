package com.gurundong.threadproject.thread.mianshi.图;

import java.util.ArrayList;
import java.util.List;

public class 邻接矩阵 {
    private List<Object> vertexList;
    private int[][] edges;
    private int numOfEdges;

    public 邻接矩阵(int n) {
        this.vertexList = new ArrayList<>(n);
        this.edges = new int[n][n];
        numOfEdges = 0;
    }

    public void insertVertex(Object vertex){
        this.vertexList.add(vertexList.size(),vertex);
    }

    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        numOfEdges++;
    }

    public int getNumOfVertex(){
        return vertexList.size();
    }

    public int getNumOfEdges(){
        return numOfEdges;
    }

    public void deleteEdge(int v1,int v2){
        edges[v1][v2] = 0;
        numOfEdges--;
    }

    public static void main(String[] args) {
        邻接矩阵 s = new 邻接矩阵(4);
        s.insertVertex("V1");
        s.insertVertex("V2");
        s.insertVertex("V3");
        s.insertVertex("V4");
        //插入四条边
        s.insertEdge(0, 1, 2);
        s.insertEdge(0, 2, 5);
        s.insertEdge(2, 3, 8);
        s.insertEdge(3, 0, 7);

        System.out.println("结点个数是："+s.getNumOfVertex());
        System.out.println("边的个数是："+s.getNumOfEdges());
        s.deleteEdge(0,1);
        System.out.println("结点个数是："+s.getNumOfVertex());
        System.out.println("边的个数是："+s.getNumOfEdges());
    }
}
