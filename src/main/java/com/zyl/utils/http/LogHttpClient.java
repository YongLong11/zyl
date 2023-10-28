package com.zyl.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogHttpClient extends AbstractHttpClient implements HttpClient {


    @Override
    protected void logInvoke(String url, String params, String response) {
        try {
            log.info("调用:【{}】 params:【{}】 response:【{}】", url, params, response);
        } catch (Exception e) {
        }
    }
}
