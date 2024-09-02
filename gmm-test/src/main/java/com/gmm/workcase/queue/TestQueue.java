package com.gmm.workcase.queue;

import java.util.Date;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Gmm
 * @date 2022/7/6
 */
public class TestQueue {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<MpTaskInfo> queue = new PriorityBlockingQueue<>();

        Thread t = new Thread(() -> {
            while (true){
                System.out.println("消费....");
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MpTaskInfo task = null;
                try {
                    task = queue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("消费到了："+task);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        System.out.println("消费启动了");

        Thread.sleep(2000);
        MpTaskInfo mpTaskInfo1 = MpTaskInfo.builder()
                .mpTaskId("uuid01")
                .submitTime(new Date())
                .isSyncTask(true)
                .build();
        queue.add(mpTaskInfo1);

        Thread.sleep(5000);

        MpTaskInfo mpTaskInfo2 = MpTaskInfo.builder()
                .mpTaskId("uuid02")
                .submitTime(new Date())
                .isSyncTask(true)
                .build();
        queue.add(mpTaskInfo2);


    }

}


