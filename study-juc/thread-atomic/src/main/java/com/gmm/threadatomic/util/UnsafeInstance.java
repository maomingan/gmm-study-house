package com.gmm.threadatomic.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeInstance {

    public static Unsafe reflectGetUnsafe(){
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
