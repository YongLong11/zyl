package com.zyl.config;

import com.zyl.advic.interceptor.ZylInterceptor;
import com.zyl.something.httpAop.annotation.HttpClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ComponentScan(basePackages = "com.zyl.client", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = HttpClient.class)
})
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    ZylInterceptor zylInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(zylInterceptor);
    }
}
