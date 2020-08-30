package org.spring.learning.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MySpringEventListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.printf("监听到Spring Application Context[ID: %s; 线程：%s] 的 %s \n",
                event.getSource(), Thread.currentThread().getName(), event.toString());
    }

}
