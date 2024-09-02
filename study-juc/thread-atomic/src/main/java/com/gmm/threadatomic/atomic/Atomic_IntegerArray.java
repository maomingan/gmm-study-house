package com.gmm.threadatomic.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子类会拷贝原来的数据一份，然后做各种原子操作。所以下面虽然修改了原子类数组的值，但是原始数组的值并未被一起修改。
 */
public class Atomic_IntegerArray {

    private static int[] arr = new int[]{1,2};
    private static AtomicIntegerArray aiArr = new AtomicIntegerArray(arr);

    public static void main(String[] args) {

        // 原子修改数组下标为0的数据
        aiArr.getAndSet(0, 100);
        System.out.println(aiArr.get(0));
        System.out.println(arr[0]);

    }

}
