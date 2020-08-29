package org.spring.learning.generic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.stream.Stream;

public class GenericApiDemo {

    public static void main(String[] args) {
        //原生类型 int long
        Class<?> clazz = int.class;

        // 数组类型 int[], Object[]
        Class<?> objectArrayClass = Object[].class;

        //原始类型
        Class<?> rawClass = String.class;

        //泛型类型
        Class<?> genericClass = ArrayList.class;

        // 泛型参数类型
        ParameterizedType parameterizedType = (ParameterizedType) genericClass.getGenericSuperclass();

        System.out.println(genericClass.toString());
        System.out.println(parameterizedType.toString());
        Stream.of(parameterizedType.getActualTypeArguments()).forEach(System.out::println);
    }

}
