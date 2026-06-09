package com.hwadee.cdutproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${maid.upload-folder}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 当访问 http://localhost:8081/xxxx.jpg 时，直接映射到本地的物理存储文件夹
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "file:" + uploadFolder);
    }
}