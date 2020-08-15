package com.spring.learning.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import java.io.IOException;
import java.io.Reader;

public class EncodedFileSystemResourceDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir") +
                "/resource/src/main/java/com/spring/learning/resource/EncodedFileSystemResourceDemo.java";
        FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
        Reader reader = encodedResource.getReader();
        System.out.println(IOUtils.toString(reader));
    }

}
