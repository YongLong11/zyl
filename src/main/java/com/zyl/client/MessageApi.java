package com.zyl.client;

import com.alibaba.fastjson.JSONObject;
import com.zyl.client.feignInterceptor.FeignRequestInterceptor;
import com.zyl.something.httpAop.annotation.HttpClient;
import com.zyl.something.httpAop.annotation.HttpInvoke;
import com.zyl.something.httpAop.annotation.PathParam;
import com.zyl.something.httpAop.annotation.ReqBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://message-api-new.kt.ziroom.com", name = "message-api", configuration = FeignRequestInterceptor.class)
public interface MessageApi {

    @PostMapping("api/work/wechat/send")
    JSONObject send(@RequestBody JSONObject request);
}

//@HttpClient(host = "http://message-api-new.kt.ziroom.com")
//public interface MessageApi {
//
//    @HttpInvoke(path = "/api/work/wechat/{send}")
//    JSONObject send(@ReqBody JSONObject request, @PathParam("send") String send);
//}
