package com.gmm.threadatomic.unsafe;

import com.gmm.threadatomic.util.UnsafeInstance;
import sun.misc.Unsafe;

/**
 * unsafe魔法类操作：内存操作
 */
public class AllocateMemoryAccess {

    public static void main(String[] args) {

        Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
        // 分配的值
        long value = 520;
        // 分配的内存大小
        byte size = 8;

        // 分配内存
        long address = unsafe.allocateMemory(size);
        System.out.println("address: " +address);

        // 将值写入到内存中
        unsafe.putAddress(address, value);

        // 从内存中读取数据
        long readValue = unsafe.getAddress(address);
        System.out.println("value: "+readValue);

        // 释放内存
        unsafe.freeMemory(address);

    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize....");
        super.finalize();
    }

}
