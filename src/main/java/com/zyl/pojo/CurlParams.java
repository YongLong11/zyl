package com.zyl.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class CurlParams {

    public String url;
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * 头信息
     */
    public Map<String, String> header;

    public String headerStr;
    /**
     * json 参数
     */
    private String data;
    /**
     * key-value
     */
    private String param;
}
