package org.spring.learning.springbean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * User 工厂类
 */

public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    // 基于@PostConstruct注解
    @PostConstruct
    public void init(){
        System.out.println("DefaultUserFactory,由PostConstruct初始化中");
    }

    @Override
    public void initUserFactory(){
        System.out.println("DefaultUserFactory,由自定义初始化方法initUserFactory初始化中");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DefaultUserFactory,由afterPropertiesSet初始化中");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy : UserFactory 销毁中");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DefaultUserFactory,由DisposableBean#destroy销毁中");
    }

    @Override
    public void destroyUserFactory(){
        System.out.println("DefaultUserFactory,由自定义初始化方法destroyUserFactory销毁中");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("DefaultUserFactory,正在被回收");
    }
}
