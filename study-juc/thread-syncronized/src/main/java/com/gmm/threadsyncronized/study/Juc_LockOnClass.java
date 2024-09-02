package com.gmm.threadsyncronized.study;

/**
 * synchronized用在静态方法上，则锁的是静态方法所在的类对象，这里就是Juc_LockOnClass.class
 */
public class Juc_LockOnClass {

    static int stock;

    public static synchronized void decrStock(){
        System.out.println(--stock);
    }

    public static void main(String[] args) {
        // 这里锁的对象是Juc_LockOnClass.class这个类对象
        Juc_LockOnClass.decrStock();
    }

}
