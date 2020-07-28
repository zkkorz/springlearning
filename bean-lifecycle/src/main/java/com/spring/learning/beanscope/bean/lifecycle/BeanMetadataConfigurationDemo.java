package com.spring.learning.beanscope.bean.lifecycle;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.nio.charset.Charset;
import java.util.Arrays;

public class BeanMetadataConfigurationDemo {

    /**
     * XML 见 AnnotationDependencyFieldInjectionDemo
     */
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        String location = "/META-INF/user.properties";
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        int beanNumber = propertiesBeanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("加载的Bean数量： " + beanNumber);
        System.out.println(beanFactory.getBeanDefinition("user"));
        System.out.println(beanFactory.getBean("user", User.class));
    }

}
