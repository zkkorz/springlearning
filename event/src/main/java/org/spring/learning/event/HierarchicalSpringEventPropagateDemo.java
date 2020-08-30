package org.spring.learning.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.LinkedHashSet;
import java.util.Set;

public class HierarchicalSpringEventPropagateDemo {

    public static void main(String[] args) {
        // 创建 Parent Spring Application Context
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.setId("parent-context");
        // 注册MyListener到 Parent Spring Application Context
        parentContext.register(MyListener.class);
        // 创建 Current Spring Application Context
        AnnotationConfigApplicationContext currentContext = new AnnotationConfigApplicationContext();
        currentContext.setId("current-context");
        // 注册MyListener到 Current Spring Application Context
        currentContext.register(MyListener.class); // 在此处声明次触发一次
        // 将current关联到parent
        currentContext.setParent(parentContext);

        currentContext.close();
        parentContext.refresh();

        currentContext.close();
        parentContext.close();
    }

    static class MyListener implements ApplicationListener<ApplicationContextEvent> {

        private static Set<ApplicationContextEvent> processedEvent = new LinkedHashSet<>();

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            if(processedEvent.add(event)){
                System.out.printf("监听到Spring Application Context[ID: %s] 的 %s \n",
                        event.getApplicationContext().getId(), event.toString());
            }
        }
    }

}
