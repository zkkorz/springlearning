package com.spring.learning.resource.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

public interface ResourceUtils {

    static String getContent(Resource resource, String encoding) throws IOException {
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        try(Reader reader = encodedResource.getReader()){
            return IOUtils.toString(reader);
        }
    }

    static String getContent(Resource resource) {
        try {
            return getContent(resource, "UTF-8");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

}
