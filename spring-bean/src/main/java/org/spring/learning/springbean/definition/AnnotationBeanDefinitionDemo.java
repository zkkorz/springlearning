package org.spring.learning.springbean.definition;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

// 3 通过 @Import 倒入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //通过BeanDefinition注册API实现
        registerBeanDefinition(applicationContext, "name-user", User.class);
        registerBeanDefinition(applicationContext, null, User.class);
        //注入 Configuration Class 配置类
        applicationContext.register(Config.class);
        //启动应用上下文
        applicationContext.refresh();
        System.out.println("Config 类型所有的Bean" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型所有的Bean" + applicationContext.getBeansOfType(User.class));
        //关闭应用上下文
        applicationContext.close();
    }

    // 2 通过 @Component 方式
    @Component //定义当前类作为Spring Bean
    public static class Config{

        // 1 通过 @Bean 方式定义
        @Bean(name = {"user", "zkk-user"})
        public User user(){
            User user = new User();
            user.setId(1L);
            user.setName("zkk");
            return user;
        }

    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        beanDefinitionBuilder.addPropertyValue("id", 1).addPropertyValue("name", "zkk");
        //注册 BeanDefinition
        if(StringUtils.hasText(beanName)){
            // 命名注册方式
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            // 非命名注册方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

}
