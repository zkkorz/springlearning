package org.spring.learning.generic;

import java.util.ArrayList;
import java.util.Collection;

public class GenericDemo {

    public static void main(String[] args) {
        Collection<String> list = new ArrayList<>();
        list.add("element 1");
        list.add("element 2");

        Collection temp = list;
        //范型擦写
        temp.add(1);

        System.out.println(list);
    }

}
