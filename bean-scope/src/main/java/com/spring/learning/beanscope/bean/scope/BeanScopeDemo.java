package com.spring.learning.beanscope.bean.scope;
import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

public class BeanScopeDemo {

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser2;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String, User> userMap;

    @Bean
    public static User singletonUser(){
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser(){
        return createUser();
    }

    private static User createUser(){
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注入 Configuration Class 配置类  ---》也是Spring Bean
        applicationContext.register(BeanScopeDemo.class);

        //启动应用上下文
        applicationContext.refresh();

        // 结论一： Singleton Bean 无论依赖查找还是依赖注入均为同一个对象； Prototype Bean 无论依赖注入还是依赖查找均为新生成对象
        // 结论二： 如果依赖注入集合类型的对象， Singleton Bean 和Prototype Bean均会生生成一个对象
        // 结论三： 无论是Singleton还是Prototype都会执行出使唤方法的回调，但是Prototype不会执行销毁方法回调
        // Spring 容器无法管理prototype bean的完整的生命周期，也没有办法记录实例的存在， 销毁回调方法将不会被执行
        // 可以利用BeanPostProcessor进行清扫工作
        scopeBeanByLookup(applicationContext);
        scopeBeanByInjection(applicationContext);

        //关闭应用上下文
        applicationContext.close();
    }

    private static void scopeBeanByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println("beanScopeDemo.singletonUser1" + beanScopeDemo.singletonUser1);
        System.out.println("beanScopeDemo.singletonUser2" + beanScopeDemo.singletonUser2);
        System.out.println("beanScopeDemo.prototypeUser2" + beanScopeDemo.prototypeUser1);
        System.out.println("beanScopeDemo.prototypeUser2" + beanScopeDemo.prototypeUser2);
        System.out.println("beanScopeDemo.userMap" + beanScopeDemo.userMap);
    }

    private static void scopeBeanByLookup(AnnotationConfigApplicationContext applicationContext) {
        for(int i = 0; i < 3; i++){
            User singletonBean = applicationContext.getBean("singletonUser", User.class);
            System.out.println(singletonBean);
        }
        for(int i = 0; i < 3; i++){
            User prototypeBean = applicationContext.getBean("prototypeUser", User.class);
            System.out.println(prototypeBean);
        }
    }

}
