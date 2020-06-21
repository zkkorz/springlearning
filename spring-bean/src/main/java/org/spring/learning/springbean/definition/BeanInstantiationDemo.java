package org.spring.learning.springbean.definition;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        User staticMethodUser = beanFactory.getBean("user-by-static-method", User.class);
        User instanceMethodUser = beanFactory.getBean("user-by-instance-method", User.class);
        User factoryBeanUser = beanFactory.getBean("user-by-factory-bean", User.class);

        System.out.println(staticMethodUser);
        System.out.println(instanceMethodUser);
        System.out.println(factoryBeanUser);
        System.out.println(staticMethodUser == instanceMethodUser);
        System.out.println(staticMethodUser == factoryBeanUser);
    }

}
