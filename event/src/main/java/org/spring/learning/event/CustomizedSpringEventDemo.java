package org.spring.learning.event;

import org.springframework.context.support.GenericApplicationContext;

public class CustomizedSpringEventDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        context.addApplicationListener(new MySpringEventListener());

        context.refresh();

        context.publishEvent("Test Customized Spring Event Demo");

        context.close();
    }

}
