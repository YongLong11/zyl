package com.zyl.something.cache.cache;

import com.alibaba.fastjson.JSON;
import com.zyl.something.cache.constant.CacheConstant;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SerialCache implements Cache{

    private final Cache cache;

    public SerialCache(Cache cache){
        this.cache = cache;
    }

    public String name(){
        return CacheConstant.SERIAL_CACHE_NAME;
    }

    public Object get(String key){
        return cache.get(key);
    }

//    @Override
//    public void put(String key, Object value){
//        cache.put(key, serialValue(value));
//    }
//    @Override
//    public void put(String key, String unless, Object value){
//        put(key, unless, value, CacheConstant.TTL, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void put(String key, Object value, long duration) {
//        put(key, null, value, duration);
//    }
//
//    @Override
//    public void put(String key, String unless, Object value, long duration) {
//        put(key, unless, value, duration, TimeUnit.SECONDS);
//    }
//    public void put(String key,  Object value, long duration, TimeUnit timeUnit){
//        put(key, null, value, duration, timeUnit);
//    }

    @Override
    public void put(String key, String unless, Object value, long duration, TimeUnit timeUnit) {
        cache.put(key, unless, serialValue(value), duration, timeUnit);
    }

    public void delete(String key){
        cache.delete(key);
    }

    private String serialValue(Object value){
        if(Objects.isNull(value)){
            return null;
        }
        return JSON.toJSONString(value);
    }
}
