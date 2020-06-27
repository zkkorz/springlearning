package com.spring.learning.dependency.lookup;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class ObjectProviderDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类作为配置类
        applicationContext.register(ObjectProviderDemo.class);//@Configuration 注解不是非必须的
        //启动应用上下文
        applicationContext.refresh();
        lookupByObjectProvider(applicationContext);
        lookupIfAvailable(applicationContext);
        lookupByStreaam(applicationContext);
        //关闭应用上下文
        applicationContext.close();
    }

    private static void lookupByStreaam(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> userObjectProvider = applicationContext.getBeanProvider(String.class);
        for(String string : userObjectProvider){
            System.out.println(string);
        }
        userObjectProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        User user = userObjectProvider.getIfAvailable(User::new);
    }

    @Bean
    @Primary
    public String helloWorld(){// 方法名就是 Bean 的名称
        return "Hello， World";
    }

    @Bean
    public String message(){// 方法名就是 Bean 的名称
        return "message";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(objectProvider.getObject());
    }


}
