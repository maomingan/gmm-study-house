package com.gmm.threadatomic.unsafe;

import com.gmm.threadatomic.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * unsafe魔法类操作：线程调度
 */
public class ThreadParker {

    static Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程启动了...");
                // false标识ns定时阻塞，true标识ms定时启动。第二个参数是时间
                unsafe.park(false, 0);
                System.out.println("子线程阻塞后，又被唤醒了!");
            }
        });
        t.start();

        Thread.sleep(3000);
        System.out.println("主线程准备唤醒子线程");
        unsafe.unpark(t);

    }

}
