package com.gmm.threadpool.forkjoin.util;

import java.util.Random;

public class RandomArrayUtils {

    private static Random random = new Random();

    public static int[] buildRandomIntArray(int size){
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

    public static int[] buildRandomIntArray(){
        int[] arr = new int[random.nextInt(100)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

}
