package org.spring.learning.event;

import org.springframework.context.support.GenericApplicationContext;

public class ApplicationListenerDemo {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        // 向Spring应用上下文注册事件
        context.addApplicationListener(applicationEvent -> {
            System.out.println("接收到Spring事件：" + applicationEvent);
        });

        //

        // 如果将refresh去除，即不启动上下文，那么就没有refresh事件和close事件，因为没有开启就不需要关闭
        context.refresh();
        // refresh 和 start的区别
        context.start();
        context.close();

    }

}
