package com.ziroom.zyl.client.apiAdaptor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageUrlConfig implements UrlConfig {

    SEND_WECHAT("api/work/wechat/send", "发送企业微信", "0");

    private String url;
    private String succsessField;
    private String successCode;

    @Override
    public String getUrl() {
        return url;
    }
    @Override
    public String getSuccessField() {
        return succsessField;
    }

    @Override
    public String getSuccessCode() {
        return successCode;
    }
}
