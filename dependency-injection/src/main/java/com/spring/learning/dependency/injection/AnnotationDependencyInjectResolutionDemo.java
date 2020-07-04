package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

public class AnnotationDependencyInjectResolutionDemo {

    @Autowired
    @Lazy
    private User lazyUser;

    @Inject
    private User injectUser;

    @MyAutowired
    private User myAutowiredUser;

    @Autowired
    private User user;//DependencyDescriptor = 必须的（required）+ 实时注入（eager=true） + 通过类型进行依赖查找 + 字段名称 user
    // + primary = true

    @Autowired
    private List<User> users;

    @Autowired
    private Optional<User> optionalUser;

    @InjectUser
    private User myInjectUser;

    /**
     * 需要优先于初始化实例化Bean，将方法修改为static
     */
    @Bean//(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        //替换原有的注解，使用新注解
        beanPostProcessor.setAutowiredAnnotationType(InjectUser.class);
        //原有注解新增注解
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new HashSet<>();
//        autowiredAnnotationTypes.add(Autowired.class);
//        autowiredAnnotationTypes.add(Inject.class);
//        autowiredAnnotationTypes.add(InjectUser.class);
//        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return beanPostProcessor;
    }

    /**
     * 需要优先于初始化实例化Bean，将方法修改为static
     */
//    @Bean
//    @Order(Ordered.LOWEST_PRECEDENCE - 3)
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        //替换原有的注解，使用新注解
//        beanPostProcessor.setAutowiredAnnotationType(InjectUser.class);
//        return beanPostProcessor;
//    }


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

        System.out.println("user：：：" + demo.user);
        System.out.println("lazyUser：：：" + demo.lazyUser);
        System.out.println("injectUser：：：" + demo.injectUser);
        System.out.println("user list：：：" + demo.users);
        System.out.println("optionalUser：：：" + demo.optionalUser);

        System.out.println("myAutowiredUser：：：" + demo.myAutowiredUser);
        System.out.println("myInjectUser：：：" + demo.myInjectUser);

        //关闭应用上下文
        applicationContext.close();
    }

}
