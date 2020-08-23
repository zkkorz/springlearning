package org.spring.learning.conversion;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringCustomizedPropertyEditorDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CustomizedPropertyEditorRegistrar.class);
        applicationContext.refresh();
        applicationContext.close();
    }

}
