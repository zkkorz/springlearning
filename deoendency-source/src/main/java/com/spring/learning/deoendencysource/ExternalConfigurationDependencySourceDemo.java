package com.spring.learning.deoendencysource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource(value = "application.properties", encoding = "UTF-8")
public class ExternalConfigurationDependencySourceDemo {

    @Value("${user.id:-1}")
    private Long id;

    @Value("${userq.name:-1}")
    private String name;

    @Value("${user.resource:classpath://application.properties}")
    private Resource resource;

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(ExternalConfigurationDependencySourceDemo.class);

        //启动应用上下文
        applicationContext.refresh();

        ExternalConfigurationDependencySourceDemo dependencySourceDemo =
                applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);

        System.out.println("id:" + dependencySourceDemo.id);
        System.out.println("resource:" + dependencySourceDemo.resource);
        System.out.println("name:" + dependencySourceDemo.name);

        //关闭应用上下文
        applicationContext.close();
    }
}
