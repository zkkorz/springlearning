package org.spring.learning.databinding;

import com.spring.learning.iocoverview.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;

import java.util.HashMap;
import java.util.Map;

public class DataBinderDemo {

    public static void main(String[] args) {
        //创建空白对象
        User user = new User();

        //创建 DataBinder
        DataBinder binder = new DataBinder(user);

        //创建PropertyValues
        Map<String, Object> source = new HashMap<>();
        source.put("id", 1);
        source.put("name", "test");

        source.put("company.name", "spring");

        // propertyValues 存在User中不存在的属性值
        // DataBinder忽略位置属性
        source.put("test", "not exists");

        // 设置ignoreUnknownFields 为false
        //binder.setIgnoreUnknownFields(false);

        // 设置ignoreInvalidFields 为false
        //binder.setAutoGrowNestedPaths(false);

        // 设置ignoreInvalidFields 为false
        //binder.setIgnoreInvalidFields(true);

        binder.setRequiredFields("id", "name", "city");

        PropertyValues propertyValues = new MutablePropertyValues(source);

        binder.bind(propertyValues);

        //输出user内容
        System.out.println(user);

        // 获取绑定结果， 结果包含错误信息，但是不抛出异常
        System.out.println(binder.getBindingResult());
    }

}
