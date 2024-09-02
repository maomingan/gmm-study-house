package com.gmm.threadpool.forkjoin.multiThreads;

import com.gmm.threadpool.forkjoin.util.RandomArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 利用计算机多核cpu的处理能力，用多线程来执行任务，数据量特别大的时候提高计算效率
 */
@Slf4j
public class SumMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int[] randomIntArray = RandomArrayUtils.buildRandomIntArray(10000);

        /**
         * 单线程处理
         */
        long result = 0;
        for (int i : randomIntArray) {
            result += i;
        }
        log.info("single thread result: "+result);

        /**
         * 多线程处理
         */
        int nCPU = Runtime.getRuntime().availableProcessors();
        int numPerThread = randomIntArray.length/(nCPU-1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(nCPU, nCPU, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        Future<Long>[] taskResults = new Future[nCPU];
        for(int i = 0; i < nCPU; i++){
            int startIndex = numPerThread*i;
            int endIndex = numPerThread*(i+1)-1>=randomIntArray.length?(randomIntArray.length-1):(numPerThread*(i+1)-1);
            log.info("task{} start:{} end:{}",i,startIndex,endIndex);
            taskResults[i] = executor.submit(new SumTask(randomIntArray, startIndex, endIndex));
        }
        // 再汇总最后的结果
        result = 0;
        for (int i = 0; i < taskResults.length; i++) {
            result += taskResults[i].get();
        }
        executor.shutdown();
        log.info("multi thread result: "+result);

    }

}
