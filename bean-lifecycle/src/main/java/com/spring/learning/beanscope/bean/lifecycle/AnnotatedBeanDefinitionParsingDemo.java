package com.spring.learning.beanscope.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

public class AnnotatedBeanDefinitionParsingDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 基于Java注解的BeanDefinitionReader 的实现
        // #AnnotatedBeanDefinitionReader 并没有实现BeanDefinitionReader，因为BeanDefinitionReader是面向资源的来源（文件资源等）
        AnnotatedBeanDefinitionReader annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();
        //注册当前类， 无component等注解标识， 知识一个普通类
        annotatedBeanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);
        int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();
        System.out.println("本次加载了" + (beanDefinitionCountAfter - beanDefinitionCountBefore) + "个BeanDefinition");
        AnnotatedBeanDefinitionParsingDemo definitionParsingDemo = beanFactory.
                getBean("annotatedBeanDefinitionParsingDemo", AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(definitionParsingDemo);
    }

}
