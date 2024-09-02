package com.gmm.threadpool.schedule;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ScheduledThreadPool的一个应用场景，用于服务定时发送心跳，比如微服务中的eureka就用到了
 */
public class EurekaClient {

    public static void main(String[] args) {

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(()->{
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1", 9999);

                HeartBeat hb = new HeartBeat();
                hb.setIp("192.168.x.x");
                hb.setPort(9999);
                hb.setAppName("阿甘服务");
                hb.setInstanceID(UUID.randomUUID().toString());

                // 序列化
                ObjectMapper objectMapper = new ObjectMapper();
                String s = objectMapper.writeValueAsString(hb);

                // 写到客户端
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(s.getBytes(StandardCharsets.UTF_8));

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 2000, TimeUnit.MILLISECONDS);

    }

}
