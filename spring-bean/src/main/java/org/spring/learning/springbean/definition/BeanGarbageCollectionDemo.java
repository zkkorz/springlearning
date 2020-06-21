package org.spring.learning.springbean.definition;

import org.spring.learning.springbean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws InterruptedException {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeaInitializationDemo.class);
        //启动应用上下文
        applicationContext.refresh();
        //关闭应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭");
        //强制触发GC
        System.gc();
        Thread.sleep(5000);
    }

}
