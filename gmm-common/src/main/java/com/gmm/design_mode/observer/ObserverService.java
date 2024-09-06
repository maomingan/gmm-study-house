package com.gmm.design_mode.observer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author Gmm
 * @date 2024/9/6
 */
@Slf4j
@Service
public class ObserverService {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public void doSomeThing(){
        log.info("正常处理某个业务逻辑...");
        // 处理完成后，通知观察者
        MyEvent myEvent = new MyEvent(this, MyEventData.builder().name("gmm").msg("该起来干活了").build());
        eventPublisher.publishEvent(myEvent);
    }

}
