package com.gmm.threadatomic.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/**
 * 引用类型的原子操作类
 */
public class Atomic_Reference {

    private static AtomicReference<String> aiRef = new AtomicReference<>("字符串");

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);
        for(int k = 0; k < 10; k++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 偶数的话则字符串最后写个+
                    UnaryOperator<String> op1 = s -> {
                        return s+"+";
                    };
                    // 奇数的话则字符串最后一位丢弃
                    UnaryOperator<String> op2 = s -> {
                        return s.substring(0, s.length()-1);
                    };
                    for(int i = 0; i < 1000; i++){
                        if(i%2==0){
                            aiRef.getAndUpdate(op1);
                        }else{
                            aiRef.getAndUpdate(op2);
                        }
                    }
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        // 如果是线程安全的，则最后的字符串结果还是：字符串
        System.out.println(aiRef);

    }

}
