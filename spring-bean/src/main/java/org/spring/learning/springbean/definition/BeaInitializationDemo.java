package org.spring.learning.springbean.definition;

import org.spring.learning.springbean.factory.DefaultUserFactory;
import org.spring.learning.springbean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class BeaInitializationDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeaInitializationDemo.class);
        //启动应用上下文
        applicationContext.refresh();
        //非延迟出手汗在Spring应用上下文启动完成后，被初始化
        System.out.println("Spring 应用上下文已启动");
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭");
        //关闭应用上下文
        applicationContext.close();
        System.out.println("Spring 应用上下文已关闭");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "destroyUserFactory")
    @Lazy(value = false)
    public UserFactory userFactory(){
        return new DefaultUserFactory();
    }

}
