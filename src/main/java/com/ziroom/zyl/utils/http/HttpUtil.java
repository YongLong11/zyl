package com.ziroom.zyl.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;


@Slf4j
public class HttpUtil {

    private final static BiFunction<Integer, String, String> callback = (k1, k2) -> {
        log.info("response code -> {} , response -> {}", k1, k2);
        if (k1 == HttpStatus.SC_OK) {
            return k2;
        }
        return null;
    };
    public static final int CONNECT_TIMEOUT = 10 * 1000;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final RequestConfig config = RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECT_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(CONNECT_TIMEOUT).build();

    /**
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData) {
        return post(url, jsonData, null, CONNECT_TIMEOUT, null, callback);
    }

    /**
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData,Map<String,String> header) {
        return post(url, jsonData, header, CONNECT_TIMEOUT, null, callback);
    }

    /**
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData, int timeout) {
        return post(url, jsonData, null, timeout, null, callback);
    }

    public static String post(Map<String,String> headers,final String url,final String jsonData) {
        return post(url, jsonData, headers, CONNECT_TIMEOUT, null, callback);
    }

    /**
     *
     * @param url
     * @param jsonData
     * @param contentType
     * @param callback 处理返回值code  返回值 的接口式函数
     * @return
     * @param <T>
     */
    public static <T> T post(final String url, final String jsonData, ContentType contentType, final BiFunction<Integer, String, T> callback) {
        return post(url, jsonData, null, CONNECT_TIMEOUT, contentType, callback);

    }

    public static <T> T post(final String url, final String jsonData, final BiFunction<Integer, String, T> callback) {
       return post(url,null, jsonData, callback);
    }

    /**
     * header 请求头设置
     */
    public static <T> T post(final String url, Map<String, String> headers ,final String jsonData, final BiFunction<Integer, String, T> callback) {
        return post(url, jsonData, headers, CONNECT_TIMEOUT, null, callback);

    }

    public static <T> T post(final String url, final String jsonData, int timeout ,final BiFunction<Integer, String, T> callback) {
        return post(url, jsonData, null, timeout, null, callback);

    }

    public static <T> T post(final String url,
                             final String jsonData,
                             Map<String,String> header,
                             Integer timeOut,
                             ContentType contentType,
                             final BiFunction<Integer, String, T> callback) {
        RequestConfig curConfig = config;
        if(timeOut != null){
            curConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .setSocketTimeout(timeOut).build();
        }
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(curConfig).build();
        HttpPost request = new HttpPost(url);
        if(contentType == null){
            contentType = ContentType.APPLICATION_JSON;
        }
        if (StringUtils.isNotEmpty(jsonData)) {
            contentType = ContentType.create(contentType.getMimeType(), "UTF-8");
            StringEntity jsonEntity = new StringEntity(jsonData, contentType);
            request.setEntity(jsonEntity);
        }

        if(header!=null && header.size()>0){
            Set keys=header.keySet();
            Iterator<String> ir=keys.iterator();
            while (ir.hasNext()){
                String key=ir.next();
                request.setHeader(key,header.get(key));
            }
        }
        return execute(client, request, callback);
    }

    /*
========================================================================================================================

     */
    /**
     * map转url参数
     */
    public static String getParameter(Map<String, Object> map) {
        StringBuffer sb=new StringBuffer();
        if(map==null || map.isEmpty()){
            return sb.toString();
        }
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(StringUtils.isNotEmpty(entry.getKey())) {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        if(sb.length()>0){
            return sb.substring(1);
        }
        return sb.toString();

    }

    /**
     * 返回对应字符串的相关get请求
     * @param
     * @return
     */
    public static String get(String url, Map<String,Object> map) {
        return get(url, map, callback);
    }

    /**
     *  返回请求的实体
     */
    public static <T> T get(String url, Map<String,Object> map, Class<T> clazz) {
        String resStr = get(url, map, callback);
        return JSON.parseObject(resStr, clazz);
    }

    public static <T> T get(final String url, Map<String,Object> map, final BiFunction<Integer, String, T> callback) {
        return get(url, getParameter(map) ,callback);
    }

    public static <T> T get(final String url, final String paramData, final BiFunction<Integer, String, T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpUriRequest request = new HttpGet(StringUtils.isEmpty(paramData) ? url : url + "?" + paramData);
        return execute(client, request, callback);
    }


    /*
========================================================================================================================
     */

    /**
     * 返回String类型 post请求 key-value值形式
     */
    public static String post(final String url, Map<String,Object> param) {

        return post(url, param, null, callback);
    }

    public static String post(final String url, Map<String, Object> param, Map<String, String> header) {
        return post(url, param, header, callback);
    }

    /**
     * post请求 key-value值形式
     */
    public static <T> T post(final String url, Map<String,Object> param, final BiFunction<Integer, String, T> callback) {
        return post(url, param, null, callback);
    }


    public static <T> T post(final String url, final Map<String, Object> param, final Map<String, String> header, final BiFunction<Integer, String, T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (param != null) {
            Set set = param.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = param.get(key);
                formparams.add(new BasicNameValuePair(key.toString(), value.toString()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formparams, UTF_8));
        if (header != null && header.size() > 0) {
            Set keys = header.keySet();
            Iterator<String> ir = keys.iterator();
            while (ir.hasNext()) {
                String key = ir.next();
                httpPost.setHeader(key, header.get(key));
            }
        }
        return execute(client, httpPost, callback);
    }


    private static <T> T execute(CloseableHttpClient client, HttpUriRequest request, final BiFunction<Integer, String, T> callback) {
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            int resultCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String resultJson = EntityUtils.toString(entity, UTF_8);
            if (callback != null) {
                return callback.apply(resultCode, resultJson);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (callback != null) {
                return callback.apply(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
            }
        } finally {
            if (null != request && !request.isAborted()) {
                request.abort();
            }
            HttpClientUtils.closeQuietly(client);
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }

}
