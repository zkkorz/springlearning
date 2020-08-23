package org.spring.learning.conversion;

import java.beans.PropertyEditor;

public class PropertyEditorDemo {

    public static void main(String[] args) {
        // 模拟Spring FrameWork 操作
        // 文本 name=test
        String text = "name = test";
        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        propertyEditor.setAsText(text);
        System.out.println(propertyEditor.getValue());
    }

}
