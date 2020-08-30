package org.spring.learning.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncEventHandlerDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        context.addApplicationListener(new MySpringEventListener());

        context.refresh();//初始化ApplicationEventMulticaster

        // 依赖查找 ApplicationEventMulticaster
        ApplicationEventMulticaster multicaster = context
                .getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                        ApplicationEventMulticaster.class);

        // 判断查找到的 ApplicationEventMulticaster 是否为 SimpleApplicationEventMulticaster
        if(multicaster instanceof SimpleApplicationEventMulticaster){
            SimpleApplicationEventMulticaster simpleApplicationEventMulticaster =
                    (SimpleApplicationEventMulticaster) multicaster;
            ExecutorService executor = Executors.newSingleThreadExecutor(
                    new CustomizableThreadFactory("my-spring-event-thread-pool"));
            // 切换 task executor --- 同步变为异步
            simpleApplicationEventMulticaster.setTaskExecutor(executor);

            // 添加 ContextClosedEvent 事件处理
            simpleApplicationEventMulticaster.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
                if(!executor.isShutdown()) {
                    executor.shutdown();
                }
            });
        }

        context.publishEvent(new MySpringEvent("Test Customized Spring Event Demo"));

        context.close();
    }

}
