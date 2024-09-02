package com.gmm.threadatomic.atomic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 原子的引用类型数组
 */
public class Atomic_ReferenceArray {

    private static Student[] arr = new Student[]{
            new Student("张三", 18),
            new Student("李四", 20)
    };
    private static AtomicReferenceArray<Student> aiRefArr = new AtomicReferenceArray<>(arr);

    public static void main(String[] args) {

        System.out.println(aiRefArr.get(0));
        // 原子修改引用类型数据
        aiRefArr.getAndSet(0, new Student("阿甘", 30));
        System.out.println(aiRefArr.get(0));

    }

    @Data
    static class Student{
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

}
