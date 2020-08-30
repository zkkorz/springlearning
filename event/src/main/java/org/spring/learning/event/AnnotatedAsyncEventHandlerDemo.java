package org.spring.learning.event;

import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
public class AnnotatedAsyncEventHandlerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(AnnotatedAsyncEventHandlerDemo.class);

        context.refresh();//初始化ApplicationEventMulticaster

        context.publishEvent(new MySpringEvent("Test Customized Spring Event Demo"));

        context.close();
    }

    @Async
    @EventListener
    public void onEvent(MySpringEvent event){
        System.out.printf("EventListener注解 监听到Spring Application Context[ID: %s; 线程：%s] 的 %s \n",
                event.getSource(), Thread.currentThread().getName(), event.toString());
    }

    @Bean
    public TaskExecutor taskExecutor(){
        return new TaskExecutorBuilder().build();
    }

}
