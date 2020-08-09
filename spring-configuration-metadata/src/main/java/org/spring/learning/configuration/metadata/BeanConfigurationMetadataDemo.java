package org.spring.learning.configuration.metadata;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

public class BeanConfigurationMetadataDemo {

    public static void main(String[] args) {
        // BeanDefinition 的定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "builder name value");
        // 获取  BeanDefinition
        // 附加属性 不对bean的生成，如实例化，属性赋值，初始化阶段
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.setAttribute("name", "test name");
        beanDefinition.setSource(BeanConfigurationMetadataDemo.class);//表示当前bean definition来自何方  辅助作用
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
                    BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
                    if (BeanConfigurationMetadataDemo.class.equals(bd.getSource())) {
                        String name = (String) bd.getAttribute("name");//对应的值是test name
                        User user = (User) bean;
                        user.setName(name);
                    }
                }
                return bean;
            }
        });

        beanFactory.registerBeanDefinition("user", beanDefinition);

        User user = beanFactory.getBean("user", User.class);

        System.out.println(user);// 如果不添加 post processor 的话 此处name输出为 builder name value ; 添加后 输出为 test name

    }

}
