package com.ziroom.zyl.common;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.ziroom.zyl.client.MessageApi;
import com.ziroom.zyl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @ClassName：MessageHelper
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/3/20 22:10
 **/
@Component
@Slf4j
public class MessageHelper {

    @Resource
    private MessageApi messageApi;
    @Value("${message.email.token}")
    private String messageEmailToken;
    @Value("${message.workwechat.token}")
    private String workWechatToken;
    @Value("${message.workwechat.modelCode}")
    private String workWechatModelCode;
    @Resource(name = "parallelPool")
    private Executor pool;

    private static final String CODE_ATTRIBUTE = "code";
    private static final String SUCCESS_CODE = "0";
    public void sendWorkWechat(String toUser, String content, boolean isUseCode) {
        CompletableFuture.runAsync(() -> {
            Preconditions.checkArgument(StringUtils.isNotBlank(toUser), "发送人为空，导致发送企业微信消息失败");
            Preconditions.checkArgument(StringUtils.isNotBlank(content), "发送内容为空，导致发送企业微信消息失败");
            JSONObject request = new JSONObject();
            request.put("pushType", "app");
            request.put("msgType", "text");
            request.put("toUser", toUser);
            request.put("token", workWechatToken);
            request.put("modelCode", workWechatModelCode);
            Map<String, Object> params = new HashMap<>(16);
            params.put("content", content);
            request.put("params", params);
            request.put("transferToUser", isUseCode);
            JSONObject response = messageApi.send(request);
            log.info("send to {} content is {} response:{}", toUser, content, response);
            if (!isSuccess(response)) {
                throw new BusinessException("推送企业微信消息失败!");
            }
        });
    }

    public boolean isSuccess(JSONObject response) {
        return Objects.equals(response.getString(CODE_ATTRIBUTE), SUCCESS_CODE);
    }
}
