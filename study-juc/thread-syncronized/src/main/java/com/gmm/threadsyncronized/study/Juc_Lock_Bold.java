package com.gmm.threadsyncronized.study;

/**
 * 【锁的粗化】
 * 下面这种情况,类下的所有方法都是同步的，且连续需要去加锁做调用，这样编译时编译器会给我们做优化，编译成类似下面：
 * public static synchronized void sout{
 *      System.out.println("sout 1");
 *      System.out.println("sout 2");
 *      System.out.println("sout 3");
 * }
 * 这样就不用每执行一个方法都有一次加锁解锁过程，从而实现优化，这叫做锁的粗化。
 */
public class Juc_Lock_Bold {

    public static void main(String[] args) {
        sout1();
        sout2();
        sout3();
    }

    public static synchronized void sout1(){
        System.out.println("sout 1");
    }

    public static synchronized void sout2(){
        System.out.println("sout 2");
    }

    public static synchronized void sout3(){
        System.out.println("sout 3");
    }

}
