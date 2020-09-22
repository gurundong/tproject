package com.gurundong.threadproject.thread.mianshi.dfs;

import java.util.ArrayList;
import java.util.List;

public class DFS1 {
    public List<Integer>[] map;
    public Boolean vis[];

    public DFS1(List<Integer>[] map,int n){
        this.map = map;
        this.vis = new Boolean[n];
    }

    public void dfs(int i){
        System.out.println(" " + i + "--->");
        vis[i] = true;
        // 遍历邻接点
        for (int j=0; j<map[i].size(); j++) {
            //System.out.println(map[i].get(j));
            if (vis[map[i].get(j)] == false) {
                this.dfs(map[i].get(j));
            }
        }
    }

    public static void main(String args[]){
        ArrayList<Integer>[] input = new ArrayList[9];
        for (int i=0;i<9;i++){
            input[i] = new ArrayList<Integer>();
        }
        input[1].add(2);
        input[2].add(1);
        input[1].add(3);
        input[3].add(1);
        input[2].add(4);
        input[4].add(2);
        input[2].add(5);
        input[5].add(2);
        input[3].add(6);
        input[6].add(3);
        input[3].add(7);
        input[7].add(3);
        input[4].add(8);
        input[8].add(4);
        input[5].add(8);
        input[8].add(5);
        input[6].add(7);
        input[7].add(6);
        DFS1 myDFSStudy = new DFS1(input, 9);
        myDFSStudy.dfs(1);
    }


}
