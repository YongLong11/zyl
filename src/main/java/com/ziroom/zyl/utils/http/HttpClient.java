package com.ziroom.zyl.utils.http;

import java.util.Map;

/**
 * @Author: J.T.
 * @Date: 2021/6/4 17:43
 * @Version 1.0
 */
public interface HttpClient {

    String post(final String url, final String jsonData);

    String get(final String url, final Map<String, Object> paramMap);

    String post(final String url, final Map<String, Object> paramMap);

    String post(final String url, final String jsonData, final Map<String, String> header);
}
