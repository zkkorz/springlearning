package com.spring.learning.iocoverview.domain;

import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User implements BeanNameAware {

    private Long id;

    private String name;

    /**
     * 当前Bean名称
     */
    private transient String beanName;

    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                '}';
    }

    public static User createUser(){
        return new User();
    }

    @PostConstruct
    public void init() {
        System.out.println("用户对象" + beanName + "初始化。。。");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("用户对象" + beanName + "销毁。。。");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
