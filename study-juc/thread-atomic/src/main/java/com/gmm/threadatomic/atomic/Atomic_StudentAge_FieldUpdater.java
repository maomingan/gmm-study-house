package com.gmm.threadatomic.atomic;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * atomic包提供的可原子修改对象属性的原子类
 */
public class Atomic_StudentAge_FieldUpdater {

    /**
     * 使用它的话，要修改的对象的成员变量必须要定义为public volatile,否则无法构建此对象
     */
    static AtomicIntegerFieldUpdater aiFU = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");

    public static void main(String[] args) {
        Student stu = new Student("阿甘", 18);
        System.out.println(aiFU.incrementAndGet(stu));
        aiFU.set(stu, 30);
        System.out.println(aiFU.get(stu));
    }

    /**
     * 这里对象的age必须定义为public volatile
     */
    @Getter
    static class Student{
        private String name;
        public volatile int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
