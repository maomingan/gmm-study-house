package com.gmm.threadcollection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap是线程不安全的，所以当多线程环境下进行put时，会丢失数据
 */
public class HashMapLoseData {

    // 线程不安全
    private static Map<Integer, Integer> map = new HashMap<>();
    // 线程安全
//    private static Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            for(int i = 0; i < 1000; i++){
                map.put(i, i);
            }
        }).start();
        new Thread(() -> {
            for(int i = 1000; i < 2000; i++){
                map.put(i, i);
            }
        }).start();

        Thread.sleep(5000);

        System.out.println("map.size: "+map.size());
        System.out.println(map.size()==2000);

    }

}
