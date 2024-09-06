package com.gmm.design_mode.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Gmm
 * @date 2024/9/6
 */
@Slf4j
@Component
public class MyListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        final MyEventData eventData = event.getEventData();
        log.info("监听器收到通知，开始处理...name:{}, msg:{}", eventData.getName(), eventData.getMsg());
        // 可以操作数据库、调用第三方等等
        log.info("监听器处理完成...");
    }
}
