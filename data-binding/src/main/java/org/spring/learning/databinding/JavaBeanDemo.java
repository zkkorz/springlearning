package org.spring.learning.databinding;

import com.spring.learning.iocoverview.domain.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.stream.Stream;

public class JavaBeanDemo {

    public static void main(String[] args) throws IntrospectionException {
        //第二个属性为截止类
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);

        //属性描述符
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(propertyDescriptor -> {
            System.out.println(propertyDescriptor.getReadMethod());
            System.out.println(propertyDescriptor.getWriteMethod());
        });
        //方法描述符
        Stream.of(beanInfo.getMethodDescriptors()).forEach(System.out::println);
    }

}
