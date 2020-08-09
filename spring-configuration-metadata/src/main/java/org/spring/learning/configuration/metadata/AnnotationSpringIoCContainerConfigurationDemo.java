package org.spring.learning.configuration.metadata;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

//将当前类作为Configuration Class
@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
@Import(User.class)
//@PropertySource("classpath:/path1")
//@PropertySource("classpath:/path2")
public class AnnotationSpringIoCContainerConfigurationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(AnnotationSpringIoCContainerConfigurationDemo.class);
        annotationConfigApplicationContext.refresh();

        Map<String, User> userMap = annotationConfigApplicationContext.getBeansOfType(User.class);
        for(Map.Entry<String, User> entry : userMap.entrySet()){
            System.out.printf("User Bean name : %s, content: %s \n", entry.getKey(), entry.getValue());
        }

        annotationConfigApplicationContext.close();
    }

}
