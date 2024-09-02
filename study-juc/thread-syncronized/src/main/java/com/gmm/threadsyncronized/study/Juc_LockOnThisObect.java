package com.gmm.threadsyncronized.study;

/**
 * synchronized用在普通方法上，则锁的是这个方法所在的类Juc_LockOnThisObect的this这个实例对象（1个this指向obj1，1个this指向obj2）
 */
public class Juc_LockOnThisObect {

    private int stock;

    public synchronized void decrStock(){
        System.out.println(--stock);
    }

    public static void main(String[] args) {

        // 这里等于是2个this锁对象，一个this指向obj1，一个this指向obj2，所以两次调用方法并不是用的同一把锁
        Juc_LockOnThisObect obj1 = new Juc_LockOnThisObect();
        obj1.decrStock();

        Juc_LockOnThisObect obj2 = new Juc_LockOnThisObect();
        obj2.decrStock();

    }

}
