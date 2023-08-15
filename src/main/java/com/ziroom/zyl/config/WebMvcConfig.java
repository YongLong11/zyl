package com.ziroom.zyl.config;

import com.ziroom.zyl.advic.interceptor.ZylInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    ZylInterceptor zylInterceptor;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(zylInterceptor);
    }
}
