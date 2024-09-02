package com.gmm.threadatomic.atomic;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.UnaryOperator;

/**
 * 引用类型属性的原子更新
 */
public class Atomic_StudentName_FieldUpdater {

    /**
     * 对象里引用类型数据（String，Object）的原子数据修改器
     */
    private static AtomicReferenceFieldUpdater aiFU = AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class,"name");

    public static void main(String[] args) {
        Student stu = new Student("阿甘", 18);
        // 修改方式1
        aiFU.getAndSet(stu,"蠢货");
        System.out.println(aiFU.get(stu));
        // 修改方式2
        UnaryOperator<String> op = s->{
            System.out.println("UnaryOperator --> "+s);
            return "大傻逼";
        };
        aiFU.getAndUpdate(stu, op);
        System.out.println(aiFU.get(stu));
    }

    /**
     * 这里对象的name必须定义为public volatile
     */
    @Getter
    static class Student{
        public volatile String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
