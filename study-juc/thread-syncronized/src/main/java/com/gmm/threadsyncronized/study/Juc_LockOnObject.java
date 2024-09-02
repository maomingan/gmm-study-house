package com.gmm.threadsyncronized.study;

/**
 * synchronized用在某段代码上，加上自定义的一个对象当锁对象，锁的是lockObj这个实例对象，而不是obj这个实例对象
 */
public class Juc_LockOnObject {

    public static Object lockObj = new Object();

    private int stock;

    public void decrStock(){
        synchronized (lockObj){
            System.out.println(--stock);
        }
    }

    public static void main(String[] args) {
        Juc_LockOnObject obj = new Juc_LockOnObject();
        obj.decrStock();
        obj.decrStock();
    }

}
