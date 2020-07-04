package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class AnnotationDependencyInjectResolutionDemo {

    @Autowired
    @Lazy
    private User lazyUser;

    @Inject
    private User injectUser;

//    @Autowired
//    private User user;//DependencyDescriptor = 必须的（required）+ 实时注入（eager=true） + 通过类型进行依赖查找 + 字段名称 user
//    // + primary = true
//
//    @Autowired
//    private List<User> users;
//
//    @Autowired
//    private Optional<User> optionalUser;

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(AnnotationDependencyInjectResolutionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载XML资源，解析并且生成BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        //启动应用上下文
        applicationContext.refresh();

        AnnotationDependencyInjectResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectResolutionDemo.class);

       // System.out.println("user：：：" + demo.user);

        //关闭应用上下文
        applicationContext.close();
    }

}
