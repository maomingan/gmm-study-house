package com.gmm.threadsyncronized.study;

/**
 * 【锁的消除】
 *  下面的情况，锁对象是在方法里定义的，即其实每个线程来执行的时候，都会new一个自己的锁，即任意两个线程间都不会有使用相同锁的可能。
 *  这样，编译器会把下面的同步给去掉，这就叫锁的消除，也是JDK给我们做的对于锁的一种优化。
 */
public class Juc_Lock_Remove {

    public void method(){

        Object obj = new Object();
        synchronized (obj){
            // 各种业务逻辑
            System.out.println("sout");
        }

    }

}
