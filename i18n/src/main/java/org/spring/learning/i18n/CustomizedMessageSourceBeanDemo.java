package org.spring.learning.i18n;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@EnableAutoConfiguration
public class CustomizedMessageSourceBeanDemo {

    /**
     * 在Spring Boot场景中， Primary Configuration Source(classes）高于 *AutoConfiguration
     */
    @Bean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource(){
        return new ReloadableResourceBundleMessageSource();
    }

    public static void main(String[] args) {
//        ConfigurableApplicationContext applicationContext =
//                SpringApplication.run(CustomizedMessageSourceBeanDemo.class, args);
        ConfigurableApplicationContext applicationContext =
                new SpringApplicationBuilder(CustomizedMessageSourceBeanDemo.class)
                .web(WebApplicationType.NONE).run(args);
        ConfigurableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if(beanFactory.containsBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)){
            System.out.println(beanFactory
                    .getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class));
            MessageSource messageSource = applicationContext
                    .getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
            System.out.println(messageSource);
        }
        applicationContext.close();
    }

}
