package org.spring.learning.springbean.definition;

import org.spring.learning.springbean.factory.DefaultUserFactory;
import org.spring.learning.springbean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 外部单体对象注册
 */
public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) throws InterruptedException {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        UserFactory userFactory = new DefaultUserFactory();
        SingletonBeanRegistry singletonBeanRegistry = applicationContext.getDefaultListableBeanFactory();
        //注册外部单利对象
        singletonBeanRegistry.registerSingleton("userFactory",userFactory);
        //启动应用上下文
        applicationContext.refresh();

        UserFactory userFactorySingleton = applicationContext.getBean("userFactory", UserFactory.class);
        System.out.println(userFactorySingleton);
        System.out.println(userFactorySingleton == userFactory);
        //关闭应用上下文
        applicationContext.close();
    }

}
