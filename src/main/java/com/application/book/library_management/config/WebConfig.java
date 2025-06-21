package com.application.book.library_management.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.application.book.library_management.interceptor.LoggingInterceptor;
import com.application.book.library_management.interceptor.Loggers;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Autowired
    private LoggingInterceptor loggingInterceptor;
    
    @Autowired
    private Loggers loggers;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**");
                
        registry.addInterceptor(loggers)
                .addPathPatterns("/**");
    }
}