package org.spring.learning.conversion;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class StringToPropertiesPropertyEditor extends PropertyEditorSupport {

    // 1： 实现 setAsText(String) 方法
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // 2： 将String类型转换为Propertie对象
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(text));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        // 3： 临时存储 Properties 对象
        setValue(properties);
    }
}
