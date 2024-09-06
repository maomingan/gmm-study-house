package com.gmm.design_mode.observer;

import org.springframework.context.ApplicationEvent;

/**
 * @author Gmm
 * @date 2024/9/6
 */
public class MyEvent extends ApplicationEvent {

    private MyEventData eventData;

    public MyEvent(Object source, MyEventData eventData) {
        super(source);
        this.eventData = eventData;
    }

    public MyEventData getEventData(){
        return eventData;
    }

}
