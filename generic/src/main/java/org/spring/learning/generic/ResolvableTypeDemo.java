package org.spring.learning.generic;

import org.springframework.core.ResolvableType;

public class ResolvableTypeDemo {

    public static void main(String[] args) {
        //StringList <- ArrayList <- AbstractList <- List <- Collection
        ResolvableType resolvableType = ResolvableType.forClass(GenericTypeResolverDemo.StringList.class);
        System.out.println(resolvableType.getSuperType());//ArrayList
        System.out.println(resolvableType.getSuperType().getSuperType());//AbstractList
        System.out.println(resolvableType.asCollection().resolve());//获取raw type
        System.out.println(resolvableType.asCollection().resolveGeneric(0));//获取范型参数类型

    }

}
