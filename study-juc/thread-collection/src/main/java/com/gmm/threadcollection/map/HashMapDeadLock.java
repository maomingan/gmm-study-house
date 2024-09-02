package com.gmm.threadcollection.map;

import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当扩容的时候，如果多线程环境下，jdk1.7的时候会可能产生链表环，而jdk1.8解决了这个问题，不可能会产生死锁
 * 测试的时候调整编译时使用jdk1.7，多执行几次，会看到死锁，程序无法执行完成退出
 */
public class HashMapDeadLock {

    private static Map<Integer, Integer> map = new HashMap<>(2);

    public static void main(String[] args) {

        for(int i = 1; i <= 10; i++){
            new Thread(new MyTask()).start();
        }

    }

    static class MyTask implements Runnable{

        private  AtomicInteger ai = new AtomicInteger();

        @Override
        public void run() {
            while(ai.get() < 1000000){
                map.put(ai.get(),ai.get());
                ai.incrementAndGet();
            }
        }

    }

}


