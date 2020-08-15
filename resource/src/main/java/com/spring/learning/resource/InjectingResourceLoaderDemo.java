package com.spring.learning.resource;

import com.spring.learning.resource.utils.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

public class InjectingResourceLoaderDemo implements ResourceLoaderAware {

    private ResourceLoader resourceLoader; // 方法一

    @Autowired
    private ResourceLoader autowiredResourceLoader; // 方法二

    @Autowired
    private AbstractApplicationContext applicationContext; // 方法三

    @PostConstruct
    public void init(){
        System.out.println("resourceLoader = autowiredResourceLoader :" + (resourceLoader == autowiredResourceLoader));
        System.out.println("resourceLoader = applicationContext :" + (resourceLoader == applicationContext));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(InjectingResourceLoaderDemo.class);
        context.refresh();
        context.close();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
