package com.zyl.something.httpAop.annotation;

import com.zyl.something.httpAop.HttpClientRegistryRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpClientRegistryRegister.class)
public @interface EnableHttpClient {
    String basePackage();

}
