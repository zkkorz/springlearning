package com.spring.learning.dependency.lookup;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类作为配置类
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);//@Configuration 注解不是非必须的
        //启动应用上下文
        applicationContext.refresh();

        displayBeanFactoryGetBean(applicationContext);
        displayObjectFactoryGetBean(applicationContext);
        displayObjectProviderIfAvailableGetBean(applicationContext);

        //关闭应用上下文
        applicationContext.close();
    }

    private static void displayObjectProviderIfAvailableGetBean(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderIfAvailableGetBean", objectProvider::getIfAvailable);
    }

    private static void displayObjectFactoryGetBean(AnnotationConfigApplicationContext applicationContext) {
        ObjectFactory<User> objectFactory = applicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetBean", objectFactory::getObject);
    }

    private static void printBeansException(String msg, Runnable runnable){
        System.err.println("=======================");
        System.err.println("Source: " + msg);
        try {
            runnable.run();
        } catch (BeansException e){
            e.printStackTrace();
            System.err.println("=======================");
        }
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory){
        printBeansException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));
    }

}
