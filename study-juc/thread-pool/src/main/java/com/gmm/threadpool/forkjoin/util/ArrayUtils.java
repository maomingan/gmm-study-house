package com.gmm.threadpool.forkjoin.util;

public class ArrayUtils {

    public static int[] buildArray(int size){
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i+1;
        }
        return arr;
    }

}
