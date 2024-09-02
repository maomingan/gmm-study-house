package com.gmm.threadcollection.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList是相对于ArrayList的线程安全的数据结构
 */
public class CopyOnWriteArrayListRunner {

    public static void main(String[] args) {

        final int NUM = Runtime.getRuntime().availableProcessors();
        // 线程不安全，一边有线程在读的话，又一边有线程在写的话，就会报错java.util.ConcurrentModificationException
//        List<String> list = new ArrayList<>();
        // 线程安全，多线程环境可使用，它对于写的操作，会拷贝一份list数据进行写，而原来的list数据继续提供读的服务
        List<String> list = new CopyOnWriteArrayList<>();
        for(int i=0; i<NUM; i++){
            list.add(i, "main_"+i);
        }

        ExecutorService service = Executors.newFixedThreadPool(NUM);
        for(int i=0; i<NUM; i++){
            service.execute(new ReadTask(list));
            service.execute(new WriteTask(list, i));
        }
        service.shutdown();

    }

    // 读线程
    static class ReadTask implements Runnable{
        private List<String> list;
        public ReadTask(List<String> list) {
            this.list = list;
        }
        @Override
        public void run() {
            for(String s : list){
                System.out.println("read : "+s);
            }
        }
    }

    // 写线程
    static class WriteTask implements Runnable{
        private List<String> list;
        private int index;
        public WriteTask(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }
        @Override
        public void run() {
            list.remove(index);
            list.add(index, "write_"+index);
        }
    }

}
