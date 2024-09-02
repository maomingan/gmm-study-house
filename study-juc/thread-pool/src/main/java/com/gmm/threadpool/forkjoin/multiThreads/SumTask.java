package com.gmm.threadpool.forkjoin.multiThreads;

import java.util.concurrent.Callable;

/**
 * 计算求和的线程任务
 */
public class SumTask implements Callable<Long> {

    private int[] arr;
    private int startIndex;
    private int endIndex;

    public SumTask(int[] arr, int startIndex, int endIndex) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Long call() throws Exception {
        // 多线程任务之间有自己需要计算的范围，不存在线程安全问题
        long sum = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
