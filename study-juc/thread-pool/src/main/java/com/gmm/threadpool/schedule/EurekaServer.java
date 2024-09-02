package com.gmm.threadpool.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class EurekaServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);

        while (true){
            Socket accept = serverSocket.accept();
//            new Thread(()->{
                try {
                    InputStream inputStream = accept.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    StringBuilder sb = new StringBuilder();
                    while((len=inputStream.read(bytes)) != -1){
                        sb.append(new String(bytes,0,len));
                    }
                    // 反序列化为对象
                    ObjectMapper objectMapper = new ObjectMapper();
                    HeartBeat heartBeat = objectMapper.readValue(sb.toString(), HeartBeat.class);
                    log.info("heart beat -> {}", heartBeat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }).start();
        }

    }

}
