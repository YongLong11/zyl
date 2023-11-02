package com.zyl.utils.httpAop.handle;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public interface RespHandle<T> extends ResponseHandler<T> {
    T handleResponse(HttpResponse var1) throws ClientProtocolException, IOException;
}
