package com.ziroom.zyl.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://message-api-new.kt.ziroom.com", name = "message-api")
public interface MessageApi {

    @PostMapping("api/work/wechat/send")
    JSONObject send(@RequestBody JSONObject request);
}
