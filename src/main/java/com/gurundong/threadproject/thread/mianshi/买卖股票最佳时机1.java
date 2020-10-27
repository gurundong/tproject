package com.gurundong.threadproject.thread.mianshi;

/**
 * 假设有一个数组，它的第i个元素是一支给定的股票在第i天的价格。如果你最多只允许完成一次交易(例如,一次买卖股票),设计一个算法来找出最大利润。
 *
 * 样例
 * 给出一个数组样例 [3,2,3,1,2], 返回 1
 */
public class 买卖股票最佳时机1 {
    public static void main(String[] args) {
        int[] prices = new int[]{3,2,5,1,7};
        int max1 = maxProfit1(prices);
        System.out.println(max1);
        int max2 = maxProfit2(prices);
        System.out.println(max2);
    }
    /**
     * 暴力法
     * @param prices
     * @return
     */
    public static int maxProfit1(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        // 最大差值
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i; j < prices.length; j++) {
                max = Math.max(max,prices[j]-prices[i]);
            }
        }
        return max;
    }

    /**
     * 动态规划
     * @param prices
     * @return
     */
    public static int maxProfit2(int[] prices){
        if(prices == null || prices.length == 0){
            return 0;
        }
        // 保证最大差值
        int max = 0;
        // 最小的值
        int minData = prices[0];
        for (int i = 1; i < prices.length; i++) {
            // 两个相邻数比较，不断更新最小值
            minData = Math.min(minData,prices[i]);
            // 判断最大差值
            max = Math.max(max,prices[i]-minData);
        }

        return max;
    }
}
