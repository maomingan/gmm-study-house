package com.gmm.threadconcurrent.tool.exchanger;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * 两个线程之间交换数据（两两交换），所以线程数必须是偶数，否则会阻塞等待新的数据进来进行数据交换。
 */
@Slf4j
public class ExchangerRunner {

    public static void main(String[] args) {

        int threadNum = 6;
        Exchanger<Integer> exchanger = new Exchanger<>();

        for(int i = 0; i < threadNum; i++){
            Integer beforeNumber = i;
            new Thread(()->{
                String tName = Thread.currentThread().getName();
                log.info("我是{}，我的初始数据是{}",tName,beforeNumber);
                try {
                    Integer afterNumber = exchanger.exchange(beforeNumber);
                    log.info("我是{}，我的初始数据是{}，我的交换后的数据是{}",tName,beforeNumber,afterNumber);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

}
