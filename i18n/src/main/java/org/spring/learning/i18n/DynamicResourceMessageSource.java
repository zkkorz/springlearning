package org.spring.learning.i18n;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 动态（更新）资源{@link org.springframework.context.MessageSource}实现
 * <p>
 *     实现步骤
 * </p>
 * 1：定位资源
 * 2：初始化 Properties 对象
 * 3: 实现org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)方法
 * 4：监听资源文件 （Java NIO 2 WatchService 实现）
 * 5：实现线程池处理文件变化
 * 6：重新装载 Properties 对象
 */
public class DynamicResourceMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    private static final String resourceFileName = "msg.properties";

    private static final String resourcePath = "META-INF/" + resourceFileName;

    private static final String ENCODING = "UTF-8";

    private final Resource messagePropertiesResource;

    private final Properties messageProperties;

    private ResourceLoader resourceLoader;

    private final ExecutorService executorService;

    public DynamicResourceMessageSource() {
        this.messagePropertiesResource = getMessagePropertiesResource();
        this.messageProperties = loadMessageProperties();
        executorService = Executors.newSingleThreadExecutor();
        //监听资源文件 （Java NIO 2 WatchService 实现）
        onMessagePropertiesChanged();
    }

    private void onMessagePropertiesChanged() {
        if(this.messagePropertiesResource.isFile()){
            //获取对应文件系统中的文件
            try {
                File messagePropertiesFile = this.messagePropertiesResource.getFile();
                Path messagePropertiesFilePath = messagePropertiesFile.toPath();
                // 获取当前OS文件系统
                FileSystem fileSystem = FileSystems.getDefault();
                // 新建WatchService
                WatchService watchService = fileSystem.newWatchService();
                // 获取资源文件所在的目录
                Path dirPath = messagePropertiesFilePath.getParent();
                // 注册 WatchService到messagePropertiesFilePath 并且关心修改时间
                dirPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                // 处理资源文件变化（异步）
                processMessagePropertiesChanged(watchService);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processMessagePropertiesChanged(WatchService watchService){
        executorService.submit(() -> {
            while (true) {
                WatchKey watchKey = watchService.take();
                try {
                    if(watchKey.isValid()){
                        for(WatchEvent<?> event : watchKey.pollEvents()){
                            Watchable watchable = watchKey.watchable();
                            Path dirPath = (Path)watchable;//事件监听的目录
                            //事件所关联的对象即注册目录的自文件（或子目录）
                            Path fileRelativePath = (Path)event.context();//事件发生源的相对路径
                            if (resourceFileName.equals(fileRelativePath.getFileName().toString())){
                                // 处理为绝对路径
                                Path filePath = dirPath.resolve(fileRelativePath);
                                File file = filePath.toFile();
                                Properties properties = loadMessageProperties(new FileReader(file));
                                synchronized (messageProperties) {
                                    messageProperties.clear();
                                    messageProperties.putAll(properties);
                                }
                            }
                        }
                    }
                } finally {
                    if(watchKey != null){
                        watchKey.reset();
                    }
                }
            }
        });
    }
    private ResourceLoader getResourceLoader(){
        return this.resourceLoader != null ? this.resourceLoader : new DefaultResourceLoader();
    }

    private Properties loadMessageProperties() {
        EncodedResource encodedResource = new EncodedResource(this.messagePropertiesResource, ENCODING);
        try {
            return loadMessageProperties(encodedResource.getReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties loadMessageProperties(Reader reader){
        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return properties;
    }

    private Resource getMessagePropertiesResource(){
        ResourceLoader resourceLoader = getResourceLoader();
        return resourceLoader.getResource(resourcePath);
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String messageFormatPattern = messageProperties.getProperty(code);
        if(StringUtils.hasText(messageFormatPattern)){
            return new MessageFormat(messageFormatPattern, locale);
        }
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) throws InterruptedException {
        DynamicResourceMessageSource messageSource = new DynamicResourceMessageSource();
        for(int i = 0; i <= 1000; i++){
            String msg = messageSource.getMessage("name", new Object[]{}, Locale.getDefault());
            System.out.println(msg);
            Thread.sleep(1000);
        }
    }
}
