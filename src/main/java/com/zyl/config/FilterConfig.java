package com.zyl.config;

import com.zyl.advic.filter.ZylFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean zylFilterConfig(){
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setFilter(new ZylFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
