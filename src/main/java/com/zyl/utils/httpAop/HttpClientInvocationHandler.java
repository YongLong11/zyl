package com.zyl.utils.httpAop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.utils.ApplicationContextUtils;
import com.zyl.utils.httpAop.annotation.*;
import com.zyl.utils.httpAop.handle.RequestHandle;
import com.zyl.utils.httpAop.handle.RespHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class HttpClientInvocationHandler implements InvocationHandler {

    private Class<?> targetClass;
    private int connectionRequestTimeout = 10 * 1000;
    private int connectTimeout = 10 * 1000;
    private int socketTimeout = 10 * 1000;
    private String reqContentType;

    private Environment environment;

    private org.apache.http.client.HttpClient client = null;

    public static final Method HASH_CODE;
    public static final Method EQUALS;
    public static final Method TO_STRING;

    static {
        Class<Object> object = Object.class;
        try {
            HASH_CODE = object.getDeclaredMethod("hashCode");
            EQUALS = object.getDeclaredMethod("equals", object);
            TO_STRING = object.getDeclaredMethod("toString");
        } catch (NoSuchMethodException e) {
            // Never happens.
            throw new Error(e);
        }
    }

    public HttpClientInvocationHandler(Class<?> targetClass, Environment environment){
        this.targetClass = targetClass;
        this.environment = environment;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.equals(HASH_CODE)) {
            return objectHashCode(proxy);
        }
        if (method.equals(EQUALS)) {
            return objectEquals(proxy, args[0]);
        }
        if (method.equals(TO_STRING)) {
            return objectToString(proxy);
        }

        HttpClient httpClient = targetClass.getAnnotation(HttpClient.class);
        String host = httpClient.host();

        // 配置文件获取 host
        if(host.startsWith("${")){
            host = environment.getProperty(host.substring(2, host.length() - 1));
        }

        HttpInvoke httpInvoke = method.getAnnotation(HttpInvoke.class);
        if(httpInvoke == null){
            return null;
        }
        String path = httpInvoke.path();
        HttpMethod requestMethod = httpInvoke.method();
        reqContentType = httpInvoke.reqContentType();
//        this.reqContentType = reqContentType.name();
//        HttpConstant respContentType = httpInvoke.respContentType();
        Class<? extends RequestHandle>[] reqHandlers = httpInvoke.reqHandlers();
        Class<? extends RespHandle>[] respHandlers = httpInvoke.respHandlers();
        Class<? extends Exception>[] exceptions = httpInvoke.retryExceptions();
        int retryTimes = httpInvoke.retryTimes();
        Parameter[] parameters = method.getParameters();
        if(!requestMethod.equals(HttpMethod.POST) && !requestMethod.equals(HttpMethod.GET)){
            throw new HttpException("no support http method");
        }
        HttpRequestBase request;

        // 路径参数，只能是string
        Map<String, String> pathParamMap = new HashMap<>(parameters.length);
        if(parameters.length > 0){
            for (int i = 0; i < args.length; i++) {
                Parameter parameter = parameters[i];
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation annotation : annotations) {
                    if(annotation instanceof PathParam){
                        // do something
                        if(StringUtils.hasLength(((PathParam) annotation).value())){
                            pathParamMap.put((((PathParam) annotation).value()), args[i].toString());
                        }else {
                            pathParamMap.put(parameter.getName(), args[i].toString());
                        }
                    }
                }
            }
        }
        // 先处理路径参数，然后生成request，因为生成 request 需要成熟的 url
        StringBuilder sbPath = new StringBuilder();
        for (String str : path.split("/")) {
            if(str.startsWith("{") && str.endsWith("}")){
                String replace = str.replace("{", "").replace("}", "");
                String paramValue = pathParamMap.get(replace);
                if(Objects.nonNull(paramValue)){
                    sbPath.append(paramValue).append("/");
                }else {
                    sbPath.append("/");
                }
            }else {
                sbPath.append(str).append("/");
            }
        }
        // 处理 host 和 路径之间的拼接问题
        String realPath = sbPath.substring(0, sbPath.length() - 1);
        if(!host.endsWith("/") && !realPath.startsWith("/")){
            realPath = "/" + realPath;
        }
        URI url = new URI(host +realPath);

        if(requestMethod.equals(HttpMethod.POST)){
            request = new HttpPost(url);
        }else {
            request = new HttpGet(url);
        }
        // 检查http请求客户端，config不符合要求则创建新的
        checkClient(httpInvoke);

        // 剩余注解处理
        if(parameters.length > 0){
            for (int i = 0; i < args.length; i++) {
                Parameter parameter = parameters[i];
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation annotation : annotations) {
                    if(annotation instanceof PathParam){
                        // do something
                    }
                    if(annotation instanceof HeaderMap){
                        handleHeader(request, args[i]);
                    }
                    if(annotation instanceof ReqBody){
                        handlePostBodys(request, args[i]);
                    }
                    if(annotation instanceof ReqParam){
                        handleGetParams(request, args[i]);
                    }
                }
            }
        }
        for (Class<? extends RequestHandle> handle : reqHandlers) {
            RequestHandle bean = ApplicationContextUtils.findBean(handle);
            try {
                bean.handle(request);
            } catch (Exception e) {
                log.error("handle params error");
                throw new HttpException(e + "");
            }
        }
        HttpResponse response = null;
        // 是应该自定义异常需要重试还是IO异常才有需要重试？？？
        do {
            try {
                response = client.execute(request);
                retryTimes = -1;
            }catch (Exception e){
                boolean contains = Arrays.asList(exceptions).contains(e.getClass());
                if(contains){
                    retryTimes--;
                }else {
                    throw e;
                }
            }
        }while (retryTimes >= 0);
        Class returnType = method.getReturnType();
        String respString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(respString, returnType);
    }
    private void handlePostBodys(HttpRequestBase request, Object arg){
        String body = JSON.toJSONString(arg);
        ContentType contentType = ContentType.create(reqContentType, "UTF-8");
        StringEntity jsonEntity = new StringEntity(body, contentType);
        if(request instanceof HttpPost){
            ((HttpPost) request).setEntity(jsonEntity);
        }
    }
    private void handleGetParams(HttpRequestBase request, Object arg){
        if(!(arg instanceof Map)){
            log.warn("@ReqParam is not map type");
            return;
        }
        String parameter = getParameter((Map<String, Object>) arg);
        request = new HttpGet(!StringUtils.hasText(parameter) ? request.getURI().toString() : request.getURI().toString() + "?" + parameter);
    }
    private void handleHeader(HttpRequestBase request, Object arg){
        if(!(arg instanceof Map)){
            log.warn("@HeaderMap is not map type");
            return;
        }
        Map<String, String> headers = (Map<String, String>) arg;
        if(Objects.nonNull(headers)){
            headers.entrySet().forEach((entry) -> {
                request.setHeader(entry.getKey(), entry.getValue());
            });
        }
    }
//    private BiConsumer<HttpRequestBase, Object> handlePostBodys =  (request, arg) ->{
//        String body = JSON.toJSONString(arg);
//        ContentType contentType = ContentType.create(reqContentType, "UTF-8");
//        StringEntity jsonEntity = new StringEntity(body, contentType);
//        if(request instanceof HttpPost){
//            ((HttpPost) request).setEntity(jsonEntity);
//        }
//    };


//    private BiConsumer<HttpRequestBase, Object> handleGetParams =  (request, arg) ->{
//        if(!(arg instanceof Map)){
//            log.warn("@ReqParam is not map type");
//            return;
//        }
//        String parameter = getParameter((Map<String, Object>) arg);
//        request = new HttpGet(!StringUtils.hasText(parameter) ? request.getURI().toString() : request.getURI().toString() + "?" + parameter);
//    };

//    private BiConsumer<HttpRequestBase, Object> handleHeader = (request, arg) ->{
//        if(!(arg instanceof Map)){
//            log.warn("@HeaderMap is not map type");
//            return;
//        }
//        Map<String, String> headers = (Map<String, String>) arg;
//        if(Objects.nonNull(headers)){
//            headers.entrySet().forEach((entry) -> {
//                request.setHeader(entry.getKey(), entry.getValue());
//            });
//        }
//    };
    private String getParameter(Map<String, Object> map) {
        StringBuffer sb=new StringBuffer();
        if(map==null || map.isEmpty()){
            return sb.toString();
        }
        for(Map.Entry<String,Object> entry:map.entrySet()){
            sb.append("&");
            if(StringUtils.hasText(entry.getKey())) {
                if(entry.getValue() instanceof List || entry.getValue() instanceof Set){
                    Object collect = ((List) entry.getValue()).stream().collect(Collectors.joining(","));
                    sb.append(collect);
                }else {
                    sb.append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
        }
        if(sb.length()>0){
            return sb.substring(1);
        }
        return sb.toString();

    }

    private void checkClient(HttpInvoke httpInvoke){
        int connectionRequestTimeout = httpInvoke.connectionRequestTimeout();
        int connectTimeout = httpInvoke.connectTimeout();
        int socketTimeout = httpInvoke.socketTimeout();
        if (!checkTimeOut(connectionRequestTimeout, connectTimeout, socketTimeout)){
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout).build();
            this.client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        }
        if(client == null){
            RequestConfig config = RequestConfig.custom()
                    .setConnectionRequestTimeout(this.connectionRequestTimeout)
                    .setConnectTimeout(this.connectTimeout)
                    .setSocketTimeout(this.socketTimeout).build();
            this.client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        }
    }

    private boolean checkTimeOut(int connectionRequestTimeout, int connectTimeout, int socketTimeout){
        return this.connectionRequestTimeout == connectionRequestTimeout &&
                this.connectTimeout == connectTimeout &&
                this.socketTimeout == socketTimeout;
    }

    public String objectClassName(Object obj) {
        return obj.getClass().getName();
    }

    public int objectHashCode(Object obj) {
        return System.identityHashCode(obj);
    }

    public boolean objectEquals(Object obj, Object other) {
        return obj == other;
    }

    public String objectToString(Object obj) {
        return objectClassName(obj) + '@' + Integer.toHexString(objectHashCode(obj));
    }
}
