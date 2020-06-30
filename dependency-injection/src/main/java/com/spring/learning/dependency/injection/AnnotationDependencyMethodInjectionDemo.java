package com.spring.learning.dependency.injection;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

public class AnnotationDependencyMethodInjectionDemo {

    private UserHolder userHolder;

    @Autowired
    public void initUserHolder(UserHolder userHolder){
        this.userHolder = userHolder;
    }

    private UserHolder userHolder2;

    @Resource
    public void initUserHolder2(UserHolder userHolder2){
        this.userHolder2 = userHolder2;
    }

    @Bean
    public UserHolder userHolder(User user){
        return new UserHolder(user);
    }

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载XML资源，解析并且生成BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        //启动应用上下文
        applicationContext.refresh();

        AnnotationDependencyMethodInjectionDemo demo = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);

        UserHolder userHolder = demo.userHolder;
        System.out.println(userHolder.toString());
        UserHolder userHolder2 = demo.userHolder2;
        System.out.println(userHolder2.toString());
        System.out.println(userHolder2 == userHolder);

        //关闭应用上下文
        applicationContext.close();
    }

}
