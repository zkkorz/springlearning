package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

public class AwareDependencyInterfaceInjectionDemo implements BeanFactoryAware, ApplicationContextAware {

    private static BeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(AwareDependencyInterfaceInjectionDemo.class);

        //启动应用上下文
        context.refresh();

        System.out.println(beanFactory == context.getBeanFactory());

        System.out.println(applicationContext == context);

        //关闭应用上下文
        context.close();
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareDependencyInterfaceInjectionDemo.beanFactory = beanFactory;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareDependencyInterfaceInjectionDemo.applicationContext = applicationContext;
    }
}
