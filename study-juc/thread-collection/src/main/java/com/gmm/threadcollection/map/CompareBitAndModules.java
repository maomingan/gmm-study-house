package com.gmm.threadcollection.map;

/**
 * 比较位运算和取模运算的效率差别
 * 因为位运算是计算机底层的根本计算，所以效率会高于+-*%这些数学计算
 */
public class CompareBitAndModules {

    // 计算次数，可以往上调，这样差距会更明显一些
    private static int number = 10000*1000;
    private static int a = 1;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        bitTest();
//        moduleTest();
        long end = System.currentTimeMillis();
        System.out.println(String.format("total spend: %s ms.", (end-start)));

    }

    public static void bitTest(){
        for(int i = 1; i <= number; i++){
            a &= i;
        }
    }

    public static void moduleTest(){
        for(int i = 1; i <= number; i++){
            a %= i;
        }
    }

}
