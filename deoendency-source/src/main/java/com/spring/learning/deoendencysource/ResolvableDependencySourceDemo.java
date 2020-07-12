package com.spring.learning.deoendencysource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init(){
        System.out.println(value);
    }

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(ResolvableDependencySourceDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            // 注册 Resolvable Dependency
            beanFactory.registerResolvableDependency(String.class, "Hello");
        });

        //启动应用上下文
        applicationContext.refresh();

//        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
//
//        if(autowireCapableBeanFactory instanceof ConfigurableListableBeanFactory){
//            ConfigurableListableBeanFactory configurableListableBeanFactory =
//                    (ConfigurableListableBeanFactory) autowireCapableBeanFactory;
//
//            // 注册 Resolvable Dependency
//            configurableListableBeanFactory.registerResolvableDependency(String.class, "Hello");
//        }

        //关闭应用上下文
        applicationContext.close();
    }
}
