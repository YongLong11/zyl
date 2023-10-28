package com.zyl.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public abstract class AbstractHttpClient implements HttpClient {

    /**
     * request type :post
     * content-type: application/json
     *
     * @param url      地址
     * @param jsonData 参数
     * @return 响应结果
     */
    @Override
    public String post(final String url, final String jsonData) {
        String response = null;
        try {
            response = HttpUtil.post(url, jsonData);
        } finally {
            logInvoke(url, jsonData, response);
        }
        return response;
    }

    /**
     * request type :get
     * content-type: x-www-form-urlencoded
     *
     * @param url      地址
     * @param paramMap 参数
     * @return 响应结果
     */
    @Override
    public String get(String url, Map<String, Object> paramMap) {
        String response = null;
        try {
            response = HttpUtil.get(url, paramMap, null);
        } finally {
            logInvoke(url, JSON.toJSONString(paramMap), response);
        }
        return response;

    }

    /**
     * request type :post
     * content-type: x-www-form-urlencoded
     *
     * @param url      地址
     * @param paramMap 参数
     * @return 响应结果
     */
    @Override
    public String post(String url, Map<String, Object> paramMap) {
        String response = null;
        try {
            response = HttpUtil.post(url, paramMap);
        } finally {
            logInvoke(url, JSON.toJSONString(paramMap), response);
        }
        return response;
    }

    @Override
    public String post(String url, String jsonData, Map<String, String> header) {
        String response = null;
        try {
            response = HttpUtil.post(url, jsonData,header);
        } finally {
            logInvoke(url, jsonData, response);
        }
        return response;
    }

    protected abstract void logInvoke(String url, String params, String response);
}
