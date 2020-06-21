package org.spring.learning.springbean.definition;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        //设置xml文件
        //启动 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
        User zkkUser = beanFactory.getBean("zkk-user", User.class);
        User user = beanFactory.getBean("user", User.class);
        System.out.println("zkk-user:" + zkkUser);
        System.out.println("user:" + user);
        System.out.println(zkkUser == user);
    }

}
