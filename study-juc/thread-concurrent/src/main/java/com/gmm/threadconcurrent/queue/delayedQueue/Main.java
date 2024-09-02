package com.gmm.threadconcurrent.queue.delayedQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        DelayQueue<MovieTicket> queue = new DelayQueue<>();

        MovieTicket ticket1 = new MovieTicket("电影票01", 10000);
        queue.put(ticket1);
        MovieTicket ticket2 = new MovieTicket("电影票02", 5000);
        queue.put(ticket2);
        MovieTicket ticket3 = new MovieTicket("电影票03", 8000);
        queue.put(ticket3);
        System.out.println("电影票全部入延迟队列完成");

        while(queue.size() > 0){
            try {
                MovieTicket t = queue.take();
                System.out.println("出票："+t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
