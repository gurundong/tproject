package com.gurundong.threadproject.thread.mianshi;

import java.util.ArrayList;

// 二进制中1的数量，例如9的二进制表示为：1001，则输出2
public class Q17 {
    public static void main(String[] args) {
        System.out.println(solution(9));
        System.out.println(solution(1));
        System.out.println(solution(2));
        System.out.println(solution(3));
        System.out.println(solution(4));
        System.out.println(solution(5));
        System.out.println(solution(6));
        System.out.println(solution(7));
        System.out.println(solution(8));
        System.out.println(solution(9));
        System.out.println(solution(10));
        System.out.println(solution(11));
        System.out.println(solution(12));
    }

    // 使用Math.pow方法，计算每一位的值
    public static int solution(int input){
        // index指的是2的多少次方
        int index = 0;
        // tmp指的是2的整次方的值
        int tmp = 0;
        // arrayList存储2的次方数，比如9的组成为2的3次方+2的0次方，则存3,0。 arrayList.size()代表二进制中1的个数
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 判断是否算出了所有1的位
        while ((input-tmp) > 0){
            // 算出高位后，需要继续算低位，并重新初始化计算的值
            input = input - tmp;
            tmp = 0;
            index = 0;
            while (tmp <= input){
                tmp = (int) Math.pow(2,index);
                index++;
            }
            // 注意实际的index值为index-2，因为上面的值多算index了
            arrayList.add(index-2);
            tmp = (int) Math.pow(2,index-2);
        }
        return arrayList.size();
    }
}
