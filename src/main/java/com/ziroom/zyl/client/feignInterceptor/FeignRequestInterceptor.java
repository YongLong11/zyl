package com.ziroom.zyl.client.feignInterceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate){
        System.out.println("通过feign请求 ->" + requestTemplate.url());
    }
}
