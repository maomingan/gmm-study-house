package com.gmm.threadpool.forkjoin.recursiveTask;

import com.gmm.threadpool.forkjoin.util.ArrayUtils;
import com.gmm.threadpool.forkjoin.util.RandomArrayUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class TSumMain {

    private static final int nCPU = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        int[] intArr = ArrayUtils.buildArray(100000);
        int[] intArr = RandomArrayUtils.buildRandomIntArray(100000);

        // 单线程计算
        long sum = 0;
        for (int i : intArr) {
            sum += i;
        }
        System.out.println("单线程计算结果："+sum);

        // forkjoin计算
        TSum tSum = new TSum(0, intArr.length, intArr);
        ForkJoinPool pool = new ForkJoinPool(nCPU); //使用的线程数
        ForkJoinTask<Long> submit = pool.submit(tSum);
        System.out.println("forkjoin计算结果："+submit.get());
        if(submit.isCompletedAbnormally()){
            System.out.println(submit.getException());
        }
        pool.shutdown();

    }

}
