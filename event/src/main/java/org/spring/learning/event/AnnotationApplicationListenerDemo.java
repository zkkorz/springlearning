package org.spring.learning.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public class AnnotationApplicationListenerDemo implements ApplicationEventPublisherAware {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(AnnotationApplicationListenerDemo.class);

        // 基于接口向Spring应用上下文注册事件
        // 向Spring应用上下文注册事件
        context.addApplicationListener(applicationEvent -> {
            println("ApplicationListener接收到Spring事件：" + applicationEvent);
        });

        // 基于ApplicationListener 注册为 Spring Bean
        context.register(MyApplicationListener.class);

        // 基于Spring注解向应用上下文注册事件， 见下面方法Bean


        // 如果将refresh去除，即不启动上下文，那么就没有refresh事件和close事件，因为没有开启就不需要关闭
        context.refresh();
        // refresh 和 start的区别
        context.start();
        context.close();

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new ApplicationEvent("Test ApplicationEventPublisher") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });
        applicationEventPublisher.publishEvent("Test Default ApplicationEventPublisher");
    }

    static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
            println("MyApplicationListener接收到Spring事件：" + applicationEvent);
        }

    }

    @EventListener
    @Order(1)
    public void onApplicationEvent1(ApplicationEvent applicationEvent){
        println("EventListener(onApplicationEvent1)接收到Spring事件：" + applicationEvent);
    }

    @EventListener
    @Order(2)
    public void onApplicationEvent2(ApplicationEvent applicationEvent){
        println("EventListener(onApplicationEvent2)接收到Spring事件：" + applicationEvent);
    }

    @EventListener
    @Async
    @Order(2)
    public void onApplicationEventAsync2(ApplicationEvent applicationEvent){
        println("EventListener(onApplicationEventAsync2)接收到Spring 异步事件：" + applicationEvent);
    }

    @EventListener
    @Async
    @Order(1)
    public void onApplicationEventAsync1(ApplicationEvent applicationEvent){
        println("EventListener(onApplicationEventAsync1)接收到Spring 异步事件：" + applicationEvent);
    }

    @EventListener
    public void onApplicationRefreshEvent(ContextRefreshedEvent applicationEvent){
        println("EventListener接收到Spring refresh事件：" + applicationEvent);
    }

    @EventListener
    public void onApplicationEvent(ContextStartedEvent applicationEvent){
        println("EventListener接收到Spring start事件：" + applicationEvent);
    }

    @EventListener
    public void onApplicationEvent(ContextClosedEvent applicationEvent){
        println("EventListener接收到Spring close事件：" + applicationEvent);
    }

    private static void println(Object printable){
        System.out.printf("线程：%s ：%s \n", Thread.currentThread().getName(), printable);
    }

}
