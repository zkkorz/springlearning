package org.spring.learning.generic;

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericTypeResolverDemo {

    public static void main(String[] args) throws NoSuchMethodException {
        //Comparable泛型参数具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, Comparable.class, "getString");
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getList");
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class, List.class, "getStringList");
    }

    public static String getString(){
        return null;
    }

    public <E> List<E> getList(){
        return null;
    }

    public static StringList getStringList(){
        return null;
    }

   static class StringList extends ArrayList<String>{//泛型参数具体化

   }

    public static void displayReturnTypeGenericInfo(Class<?> containingClass, Class<?> genericClass, String methodName, Class... argumentTypes) throws NoSuchMethodException {
        Method method = GenericTypeResolverDemo.class.getMethod(methodName);

        // 声明类
        Class<?> returnType = GenericTypeResolver.resolveReturnType(method, GenericTypeResolverDemo.class);
        System.out.printf("GenericTypeResolver.resolveReturnType(%s, %s), Result: %s\n",
                methodName, containingClass.getSimpleName(), returnType);

        // 常规类不具备泛型参数类型
        Class<?> returnTypeArgument = GenericTypeResolver.resolveReturnTypeArgument(method, genericClass);
        System.out.printf("GenericTypeResolver.resolveReturnTypeArgument(%s, %s), Result: %s\n",
                methodName, containingClass.getSimpleName(), returnTypeArgument);

        Map<TypeVariable, Type> typeVariableTypeMap = GenericTypeResolver.getTypeVariableMap(StringList.class);
        System.out.println(typeVariableTypeMap);
    }

}
