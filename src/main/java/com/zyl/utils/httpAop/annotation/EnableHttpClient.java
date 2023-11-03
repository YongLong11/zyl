package com.zyl.utils.httpAop.annotation;

import com.zyl.utils.httpAop.HttpClientRegistryProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpClientRegistryProcessor.class)
public @interface EnableHttpClient {
    String basePackage();

}