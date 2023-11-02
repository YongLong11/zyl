package com.zyl.client.feignInterceptor;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
/*
暂时未生效
 */
@Configuration
public class RespInterceptor implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println("==================");
        System.out.println(methodKey);
        System.out.println("==================");
        System.out.println(response);
        return new RuntimeException();
    }
}
