package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

public class QualifierAnnotationDependencyInjectDemo {
    @Bean
    @Qualifier //进行逻辑分组
    public User user1(){
        User user = new User();
        user.setName("001");
        return user;
    }

    @Bean
    @Qualifier //进行逻辑分组
    public User user2(){
        User user = new User();
        user.setName("002");
        return user;
    }

    @Bean
    @UserGroup //进行逻辑分组
    public User user3(){
        User user = new User();
        user.setName("003");
        return user;
    }


    @Autowired
    private User superUser;

    @Qualifier("user")
    @Autowired
    private User user;//因为 superUser 是 primary的 所以如果不进行指定的话 注入的是superUser， 使用Qualifier进行指定

    /**
     * 整个上下文存在4个User类型的Bean
     *
     * superUser
     * user
     * user1
     * user2
     */

    @Autowired
    private List<User> allUsers;//4个Bean

    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers;//3个Bean = user1 + user2 + user3

    @Autowired
    @UserGroup
    private Collection<User> userGroup;//1个Bean = user3




    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(QualifierAnnotationDependencyInjectDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载XML资源，解析并且生成BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        //启动应用上下文
        applicationContext.refresh();

        QualifierAnnotationDependencyInjectDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectDemo.class);

        System.out.println("user：：：" + demo.user);
        System.out.println("superUser：：：" + demo.superUser);


        System.out.println("allUser：：：" + demo.allUsers);
        System.out.println("qualifierUser：：：" + demo.qualifierUsers);
        System.out.println("userGroup：：：" + demo.userGroup);

        //关闭应用上下文
        applicationContext.close();
    }

}
