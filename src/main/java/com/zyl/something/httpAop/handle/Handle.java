package com.zyl.something.httpAop.handle;

import org.apache.http.client.methods.HttpRequestBase;

public interface Handle<HttpRequestBase> {

    void handle(org.apache.http.client.methods.HttpRequestBase requestBase) throws Exception;
}
