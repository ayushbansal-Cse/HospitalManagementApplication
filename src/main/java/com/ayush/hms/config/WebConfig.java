package com.ayush.hms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor =
            new AuthInterceptor();


    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {

        registry.addInterceptor(
                authInterceptor
        );
    }
}