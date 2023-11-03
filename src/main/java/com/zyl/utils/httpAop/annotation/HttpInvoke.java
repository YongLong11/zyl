package com.zyl.utils.httpAop.annotation;

import com.zyl.utils.httpAop.handle.RequestHandle;
import com.zyl.utils.httpAop.handle.RespHandle;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface HttpInvoke {
    // 请求的 URL
    String path();
    // 请求类型
    HttpMethod method() default HttpMethod.POST;

    String reqContentType() default MediaType.APPLICATION_JSON_VALUE;
    //响应内容类型
//    HttpConstant respContentType() default HttpConstant.APPLICATION_JSON;

    //请求处理器
    Class<? extends RequestHandle>[] reqHandlers() default {};

    //响应处理器
    Class<? extends RespHandle>[] respHandlers() default {};

    // 发生以下异常需要重试
    Class<? extends Exception>[] retryExceptions() default IOException.class;
    // 重试次数
    int retryTimes() default 0;
    // 获取连接时间
    int connectionRequestTimeout() default 10 * 1000;
    // 建立连接超时时间
    int connectTimeout() default 10 * 1000;
    // 数据读取超时时间
    int socketTimeout() default 10 * 1000;

//    String value();
}
