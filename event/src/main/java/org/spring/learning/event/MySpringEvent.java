package org.spring.learning.event;

import org.springframework.context.ApplicationEvent;

public class MySpringEvent extends ApplicationEvent {

    public MySpringEvent(String message) {
        // 事件创建的线程
        super(String.format("[线程 : %s] : %s", Thread.currentThread().getName(), message));
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }

    public String getMessage(){
        return getSource();
    }

}
