package com.spring.learning.iocoverview.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import com.spring.learning.iocoverview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class DependencyInjectionDemo {

    public static void main(String[] args){
        //设置xml文件
        //启动 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        //自定义bean
        UserRepository userRepository = beanFactory.getBean("UserRepository", UserRepository.class);
        System.out.println(userRepository);
        //内建的依赖
        //这个歌表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == beanFactory);
        //System.out.println(beanFactory.getBean(BeanFactory.class));
        ObjectFactory<User> userObjectFactory = userRepository.getUserObjectFactory();
        System.out.println(userObjectFactory.getObject());
        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
        System.out.println(objectFactory.getObject());
        System.out.println(objectFactory.getObject() == beanFactory);
        //容器内建的Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("获取Environment类型的Bean" + environment);
    }

}
