package com.ziroom.zyl.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
public class HttpUtil {

    public static final int CONNECT_TIMEOUT = 10 * 1000;

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static final RequestConfig config = RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECT_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(CONNECT_TIMEOUT).build();


    public static <T> T post(final String url, final String jsonData, ContentType contentType, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            contentType= ContentType.create(contentType.getMimeType(), "UTF-8");
            StringEntity jsonEntity = new StringEntity(jsonData, contentType);
            request.setEntity(jsonEntity);
        }
        return execute(client, request, callback);
    }

    public static <T> T post(final String url, final String jsonData, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            request.setEntity(jsonEntity);
        }
        return execute(client, request, callback);
    }

    /**
     * header 请求头设置
     */
    public static <T> T post(final String url, final String jsonData, final ResponseCallback<T> callback,Map<String, String> headers) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            request.setEntity(jsonEntity);
        }
        if(Objects.nonNull(headers) && CollectionUtils.isNotEmpty(headers.entrySet())){
            for(String key: headers.keySet()){
                request.addHeader(key,headers.get(key));
            }
        }

        return execute(client, request, callback);
    }

    public static <T> T post(final String url, final String jsonData, final ResponseCallback<T> callback, int timeout) {
        RequestConfig customConfig =
                RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(customConfig).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
            request.setEntity(jsonEntity);
        }
        log.info("post custom timeout:{}",timeout);
        return execute(client, request, callback);
    }

    public static <T> T postStr(final String url, final String jsonData, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_FORM_URLENCODED);
            request.setEntity(jsonEntity);
        }
        return execute(client, request, callback);
    }

    /**
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData) {
        return post(url, jsonData, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("请求结果为：resultJson" + resultJson);
                if (resultCode== HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        });
    }

    /**
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData,int timeout) {
        return post(url, jsonData, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("请求结果为：resultJson" + resultJson);
                if (resultCode == HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        }, timeout);
    }

    /**
     * 返回String类型 post请求 key-value值形式
     */
    public static String post(final String url, Map<String,Object> param) {
        return post(url, param, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
            log.debug("post 请求结果为:" + resultJson);
            if (resultCode== HttpStatus.SC_OK) {
                return resultJson;
            }
            return null;
            }
        });
    }

    public static String post(final String url, Map<String, Object> param, Map<String, String> header) {
        return post(url, param, header, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("post 请求结果为:" + resultJson);
                if (resultCode== HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        });
    }

    /**
     * post请求 key-value值形式
     */
    public static <T> T post(final String url, Map<String,Object> param, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if(param!=null) {
            Set set = param.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = param.get(key);
                formparams.add(new BasicNameValuePair(key.toString(), value.toString()));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(formparams,UTF_8));
        return execute(client, httpPost, callback);
    }

    public static String post(Map<String,String> headers,final String url,final String jsonData) {
        return post(url, jsonData, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("请求结果为：resultJson" + resultJson);
                if (resultCode == HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        },headers);
    }

    /**
     * 返回对应字符串的相关get请求
     * @param
     * @return
     */
    public static String get(String url, Map<String,Object> map) {
        return get(url, map, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("get 请求结果为:" + resultJson);
                if (resultCode== HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        });
    }

    /**
     *  返回请求的实体,依赖gson
     */
    public static <T> T get(String url, Map<String,Object> map, final Type typeOfT) {
        String resStr = get(url, map, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("get 请求结果为:" + resultJson);
                if (resultCode== HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        });
        return JSON.parseObject(resStr,typeOfT);
    }

    public static <T> T get(final String url, final String paramData, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpUriRequest request = new HttpGet(StringUtils.isEmpty(paramData) ? url : url + "?" + paramData);
        return execute(client, request, callback);
    }

    public static <T> T get(final String url, Map<String,Object> map, final ResponseCallback<T> callback) {
      return get(url,getParameter(map),callback);
    }

    private static <T> T execute(CloseableHttpClient client, HttpUriRequest request, final ResponseCallback<T> callback) {
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            int resultCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String resultJson = EntityUtils.toString(entity, UTF_8);
            if (callback != null) {
                return callback.onResponse(resultCode, resultJson);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (callback != null) {
                return callback.onResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toString());
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
    /**
     * map转url参数
     */
    public static String  getParameter(Map<String, Object> map) {
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
     * 返回String类型，post请求json格式
     */
    public static String post(final String url, final String jsonData,Map<String,String> header) {
        return post(url, header,jsonData, new ResponseCallback<String>() {
            @Override
            public String onResponse(int resultCode, String resultJson) {
                log.debug("请求结果为：resultJson" + resultJson);
                if (resultCode== HttpStatus.SC_OK) {
                    return resultJson;
                }
                return null;
            }
        });
    }
    public static <T> T post(final String url, Map<String,String> header,final String jsonData, final ResponseCallback<T> callback) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost request = new HttpPost(url);
        if (StringUtils.isNotEmpty(jsonData)) {
            StringEntity jsonEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
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


    public static <T> T post(final String url, final Map<String, Object> param, final Map<String, String> header, final ResponseCallback<T> callback) {
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

}
