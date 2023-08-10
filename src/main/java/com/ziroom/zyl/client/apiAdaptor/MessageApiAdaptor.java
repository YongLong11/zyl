package com.ziroom.zyl.client.apiAdaptor;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.ziroom.zyl.client.apiAdaptor.enums.MessageUrlConfig;
import com.ziroom.zyl.utils.http.HttpClient;
import com.ziroom.zyl.utils.http.ResponseParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class MessageApiAdaptor {
    @Value("${message.email.token}")
    private String messageEmailToken;
    @Value("${message.workwechat.token}")
    private String workWechatToken;
    @Value("${message.workwechat.modelCode}")
    private String workWechatModelCode;
    @Resource(name = "logHttpClient")
    private HttpClient httpClient;
    public void sendWorkWechat(String toUser, String content, boolean isUseCode) {
        CompletableFuture.runAsync(() -> {
            Preconditions.checkArgument(StringUtils.isNotBlank(toUser), "发送人为空，导致发送企业微信消息失败");
            Preconditions.checkArgument(StringUtils.isNotBlank(content), "发送内容为空，导致发送企业微信消息失败");
            HashMap<String, Object> map = new HashMap<>();
            map.put("pushType", "app");
            map.put("msgType", "text");
            map.put("toUser", toUser);
            map.put("token", workWechatToken);
            map.put("modelCode", workWechatModelCode);
            Map<String, Object> params = new HashMap<>(16);
            params.put("content", content);
            map.put("params", params);
            map.put("transferToUser", isUseCode);
            String response = httpClient.post(MessageUrlConfig.SEND_WECHAT.getUrl(), map);
            ResponseParser.parse(response, JSONObject.class, MessageUrlConfig.SEND_WECHAT);
        }, Executors.newSingleThreadExecutor());

    }


}
