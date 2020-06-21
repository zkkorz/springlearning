package org.spring.learning.springbean.definition;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        //通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        //通过属性设置操作
        beanDefinitionBuilder.addPropertyValue("name", "zkk");
        beanDefinitionBuilder.addPropertyValue("age", 18);
        //获取 BeanDefinition 实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //BeanDefinition不是Beam的最终状态，所以仍旧可以自定义修改


        //通过 AbstractBeanDefinition的派生
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        //设置bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        //设置属性 通过 MutablePropertyValues 操作属性
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.addPropertyValue("name", "zkk");
        mutablePropertyValues.addPropertyValue("age", 18);
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
    }

}
