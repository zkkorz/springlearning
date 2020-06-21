package com.spring.learning.iocoverview.dependency.lookup;

import com.spring.learning.iocoverview.annotation.Super;
import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class DependencyLookupDemo {

    public static void main(String[] args){
        //设置xml文件
        //启动 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        lookupRealTime(beanFactory);
        lookupLazy(beanFactory);
        lookupByType(beanFactory);
        lookupCollectionByType(beanFactory);
        lookupByAnnotationType(beanFactory);
    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找标注 @Super 注解的所有的User对象:" + users);
        }
    }

    /**
     * 按类型查找集合对象
     */
    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到所有的User对象:" + users);
        }
    }

    /**
     * 按类型查找
     */
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("lookup by type:" + user.toString());
    }

    /**
     * 按名称延迟查找
     */
    private static void lookupLazy(BeanFactory beanFactory){
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("Lazy:" + user.toString());
    }

    /**
     * 按名称实时查找
     */
    private static void lookupRealTime(BeanFactory beanFactory){
        User user = beanFactory.getBean("user", User.class);
        System.out.println("RealTime" + user.toString());
    }

}
