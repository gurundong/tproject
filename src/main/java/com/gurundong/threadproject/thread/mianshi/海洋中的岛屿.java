package com.gurundong.threadproject.thread.mianshi;


public class 海洋中的岛屿 {
    public static void main(String[] args) {
        int[][] arr = {
                {0,0,1,0,1,0},
                {1,1,1,0,1,0},
                {0,0,0,0,0,0}
        };
        int n = countIsLand(arr);
        System.out.println(n);
    }

    public static int countIsLand(int[][] arr){
        if(arr==null||arr.length==0||arr[0].length==0){
            return 0;
        }
        int res=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j]==1){
                    //coverIsland(arr,i,j);
                    change(arr,i,j);
                    res++;
                }
            }
        }
        return res;
    }

    public static int[][] change(int[][] grid, int i, int j) {
        // 修改为0
        grid[i][j] = 0;

        if (i > 0 && grid[i - 1][j] == 1) {
            // 修改左边的
            grid = change(grid, i - 1, j);
        }
        if (j < grid[i].length - 1 && grid[i][j + 1] == 1) {
            // 修改右边的
            grid = change(grid, i, j + 1);
        }
        if (j > 0 && grid[i][j - 1] == 1) {
            // 修改上边的
            grid = change(grid, i, j - 1);
        }
        if (i < grid.length - 1 && grid[i + 1][j] == 1) {
            // 修改下边的
            grid = change(grid, i + 1, j);
        }

        return grid;
    }
}
