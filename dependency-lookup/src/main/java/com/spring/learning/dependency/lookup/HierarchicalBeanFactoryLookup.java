package com.spring.learning.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HierarchicalBeanFactoryLookup {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类作为配置类
        applicationContext.register(ObjectProviderDemo.class);//@Configuration 注解不是非必须的

        //获取 HierarchicalBeanFactory <- ConfigurableBeanFactory -< ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

        //是指Parent FactoryBean
        HierarchicalBeanFactory parentFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentFactory);
        System.out.println("当前 BeanFactory 的 Parent BeanFactory : " + beanFactory.getParentBeanFactory());

        displayLocalBean(beanFactory, "user");
        displayLocalBean(parentFactory, "user");
        displayContainsBean(beanFactory, "user");
        displayContainsBean(parentFactory, "user");

        //启动应用上下文
        applicationContext.refresh();
        //关闭应用上下文
        applicationContext.close();
    }

    private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName){
        System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s] : %s\n",
                beanFactory,beanName, containsBean(beanFactory, beanName));
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName){
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory){
            HierarchicalBeanFactory parentHierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
            if(containsBean(parentHierarchicalBeanFactory, beanName)){
                return true;
            }
        }
        return beanFactory.containsLocalBean(beanName);
    }

    private static void displayLocalBean(HierarchicalBeanFactory beanFactory, String beanName){
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local bean[name : %s] : %s\n",
                beanFactory,beanName, beanFactory.containsLocalBean(beanName));
    }

    private static HierarchicalBeanFactory createParentBeanFactory(){
        //创建BeanFactory容器
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //加载配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // XML 配置文件的 ClassPath 路径
        String location = "classpath:/META-INF/dependency-injection-context.xml";
        //加载资源
        xmlBeanDefinitionReader.loadBeanDefinitions(location);
        return defaultListableBeanFactory;
    }

}
