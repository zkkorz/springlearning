package com.spring.learning.beanscope.bean.lifecycle;

import com.spring.learning.iocoverview.domain.SuperUser;
import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

public class BeanInstantiationLifecycleDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加 BeanProcessor实现
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String location = "META-INF/dependency-lookup-context.xml";
        Resource resource = new ClassPathResource(location);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        int beanNumber = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("加载的Bean数量： " + beanNumber);

        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

        User superUser = beanFactory.getBean("superUser", User.class);
        System.out.println(superUser);
    }

    static class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

        /**
         * 之后就没有 create bean了
         */
        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            if(ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)){
                return new SuperUser();
            }
            return null;
        }

        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
            if(ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())){
                User user = (User) bean;
                user.setId(1111L);
                // user 对象不允许属性赋值 配置元信息 --》 属性值
                return false;
            }
            return true;
        }

        /**
         * 受限于postProcessAfterInstantiation
         */
        @Override
        public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
                throws BeansException {
            if(ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())){
                MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
                mutablePropertyValues.add("name", "test postProcessProperties");
                return mutablePropertyValues;
            }
            return null;
        }

    }

}
