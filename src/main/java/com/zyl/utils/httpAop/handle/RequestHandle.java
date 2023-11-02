package com.zyl.utils.httpAop.handle;

import org.apache.http.client.methods.HttpRequestBase;

public interface RequestHandle<HttpRequestBase> extends Handle<org.apache.http.client.methods.HttpRequestBase>{
    void handle(org.apache.http.client.methods.HttpRequestBase requestBase) throws Exception;
}
