package com.gmm.threadpool.fiber;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 当线程或者纤程数量越多时，能看到纤程由于是保存原有线程的栈信息,而不需要上下文的切换，则效率会更高。
 * 但是实际生产中，基本不会去用纤程，用处并没有那么大，当时只是炒概念而已。
 */
public class QuasarFiberRunner {

    public static void main(String[] args) throws InterruptedException {
        final int count = 1000;
//        testThreadPool(count);
        testFiber(count);
    }

    /**
     * 测试使用多线程来处理
     * @param count
     * @throws InterruptedException
     */
    public static void testThreadPool(int count) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(count);
        LongAdder latency = new LongAdder();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        long t = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            executorService.submit(()->{
                long duration = System.currentTimeMillis();
                try {
                    m1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                duration = System.currentTimeMillis() - duration;
                latency.add(duration);
                latch.countDown();
            });
        }
        latch.await();
        t = System.currentTimeMillis() - t;
        double d = (double)latency.longValue()/count;
        System.out.println(String.format("testThreadPool total time: %s, per time: %s ms",t,d));
        executorService.shutdown();
    }

    /**
     * 测试使用纤程来处理
     * @param count
     * @throws InterruptedException
     */
    public static void testFiber(int count) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(count);
        LongAdder latency = new LongAdder();
        long t = System.currentTimeMillis();
        for(int i = 0; i < count; i++){
            new Fiber<Void>("Caller", new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    long duration = System.currentTimeMillis();
                    m1();
                    duration = System.currentTimeMillis() - duration;
                    latency.add(duration);
                    latch.countDown();
                }
            }).start();
        }
        latch.await();
        t = System.currentTimeMillis() - t;
        double d = (double)latency.longValue()/count;
        System.out.println(String.format("testFiber total time: %s, per time: %s ms",t,d));
    }

//    @Suspendable
    public static void m1() throws InterruptedException {
        String m = "m1";
        m = m2();
    }
    public static String m2() throws InterruptedException {
        String m = m3();
        Thread.sleep(1000);
        return m;
    }
//    @Suspendable
    public static String m3(){
        List l = Stream.of(1,2,3).filter(x->x%2==0).collect(Collectors.toList());
        return l.toString();
    }
}
