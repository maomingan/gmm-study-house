package com.gmm.threadatomic.atomic;

import com.gmm.threadatomic.util.UnsafeInstance;
import lombok.Getter;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

/**
 * 多线程并发的环境下，我们对自己的创建的共享对象的属性比如age进行修改时，是线程不安全的。
 * 如果需要，我们可以按照下面的方式来构建我们的类，这样共享的这个类对象，在并发环境下，使用自定义的casAge()方法对age的修改也就变成了线程安全的了，等于底层修改时都是去用的unsafe的cas方法。
 * 如果不想这么麻烦的手动处理我们的类，也可以直接使用原子包里的比如：AtomicIntegerFieldUpdater
 */
public class Atomic_StudentAge_updater {

    public static void main(String[] args) {
        Student obj = new Student("阿甘", 18);
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        // 失败case
        casAge(obj, 19, 30);
        System.out.println("我现在的年龄："+obj.getAge());
        // 成功case
        casAge(obj, 18, 30);
        System.out.println("我现在的年龄："+obj.getAge());
    }

    /**
     * 原子类的底层实现都是去使用unsafe类的本地方法来实现原子性，这里底层使用的是compareAndSwapInt，其中的参数valueOffset是你要修改的这个对象属性所在这个对象的偏移位置。
     * 这里offset是12，可以通过打印对象信息就能看到offset=12开始就是age的内存空间。
     */
    private static Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static long valueOffset;

    static{
        try {
            valueOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("age"));
            System.out.println("valueOffset --> "+valueOffset);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void casAge(Object obj, int oldAge, int newAge){
        unsafe.compareAndSwapInt(obj, valueOffset, oldAge, newAge);
    }

    @Getter
    static class Student{
        private String name;
        private volatile int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
