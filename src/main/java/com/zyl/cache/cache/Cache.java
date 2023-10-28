package com.zyl.cache.cache;

import java.util.concurrent.TimeUnit;

public interface Cache {

    String name();

    Object get(String key);
//    void put(String key, Object value);
//    void put(String key, String unless, Object value);
//    void put(String key,  Object value, long duration);
//    void put(String key, String unless, Object value, long duration);
//    void put(String key,  Object value, long duration, TimeUnit timeUnit);
    void put(String key, String unless, Object value, long duration, TimeUnit timeUnit);

    void delete(String key);
}
