package com.ziroom.zyl.client.feignInterceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignRequestInterceptor implements RequestInterceptor {
    public void apply(RequestTemplate requestTemplate){
        System.out.println(requestTemplate.url());
    }
}
