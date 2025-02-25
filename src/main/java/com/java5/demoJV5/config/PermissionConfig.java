package com.java5.demoJV5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.java5.demoJV5.component.PermissionComponent;

@Configuration // Bổ sung annotation để Spring Boot nhận diện config này
public class PermissionConfig implements WebMvcConfigurer {
    @Autowired
    PermissionComponent permissionComponent;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionComponent)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/product", "/product/detail", "/contact", "/forgot-password", "/present", "/contact", "/");
    }
}

