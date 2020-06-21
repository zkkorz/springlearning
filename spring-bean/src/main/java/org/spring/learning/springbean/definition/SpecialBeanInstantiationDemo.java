package org.spring.learning.springbean.definition;

import org.spring.learning.springbean.factory.DefaultUserFactory;
import org.spring.learning.springbean.factory.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);
        demoServiceLoader();
        printServiceLoad(serviceLoader);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        UserFactory userFactory = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());
    }

    public static void demoServiceLoader(){
        ServiceLoader<UserFactory> userFactories = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        printServiceLoad(userFactories);
    }

    public static void printServiceLoad(ServiceLoader<UserFactory> userFactories){
        Iterator<UserFactory> iterator = userFactories.iterator();
        while (iterator.hasNext()){
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }
    }

}
