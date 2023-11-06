package com.zyl.something.httpAop.handle;


public interface RequestHandle<HttpRequestBase> extends Handle<HttpRequestBase>{
    void handle(HttpRequestBase requestBase) throws Exception;
}
