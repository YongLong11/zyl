package com.zyl.something.cache.cache;

import com.zyl.something.cache.constant.CacheConstant;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.TimeUnit;

public class PrefixKeyCache implements Cache{

    private final Cache cache;

    public PrefixKeyCache(Cache cache){
        this.cache = cache;
    }

    public String name(){
        return CacheConstant.PREFIX_CACHE_NAME;
    }

    public Object get(String key){
        return cache.get(appendKey(key));
    }

//    @Override
//    public void put(String key, Object value){
//        cache.put(appendKey(key), value);
//    }
//    @Override
//    public void put(String key, String unless, Object value){
//        cache.put(appendKey(key), unless, value);
//    }
//
//    @Override
//    public void put(String key, Object value, long duration) {
//        put(key, null, value, duration, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void put(String key, String unless, Object value, long duration) {
//        put(key, unless, value, duration,  TimeUnit.SECONDS);
//    }
//
//    @Override
//    public void put(String key, Object value, long duration, TimeUnit timeUnit) {
//        put(key, null, value, duration, timeUnit);
//    }

    @Override
    public void put(String key, String unless, Object value, long duration, TimeUnit timeUnit) {
        cache.put(appendKey(key), unless, value, duration, timeUnit);
    }
    public void delete(String key){
        cache.delete(appendKey(key));
    }

    private String appendKey(String key){
        if(StringUtils.isNotBlank(key)){
            return CacheConstant.PREFIX_KEY + key;
        }
        return null;
    }
}
