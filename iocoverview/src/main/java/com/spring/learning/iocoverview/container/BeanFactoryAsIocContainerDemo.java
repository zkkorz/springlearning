package com.spring.learning.iocoverview.container;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class BeanFactoryAsIocContainerDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //加载配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // XML 配置文件的 ClassPath 路径
        String location = "classpath:/META-INF/dependency-injection-context.xml";
        //加载资源
        int beanDefinitionCount = xmlBeanDefinitionReader.loadBeanDefinitions(location);
        System.out.println(beanDefinitionCount);
        lookupCollectionByType(defaultListableBeanFactory);
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

}
