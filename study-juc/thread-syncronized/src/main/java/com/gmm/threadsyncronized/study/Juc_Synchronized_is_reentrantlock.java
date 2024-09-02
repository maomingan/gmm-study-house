package com.gmm.threadsyncronized.study;

/**
 * synchronized关键字加的锁，是一个可重入锁，即可以多次加同一把锁
 */
public class Juc_Synchronized_is_reentrantlock {

    private final static Object object = new Object();

    private static void reentrantLock(){
        String t_name = Thread.currentThread().getName();
        synchronized (object){
            System.out.println(String.format("%s hold %s -> monitor lock", t_name, object));
            synchronized (object){
                System.out.println(String.format("%s re-hold %s -> monitor lock", t_name, object));
            }
        }
    }

    public static void main(String[] args) {
        reentrantLock();
    }

}
