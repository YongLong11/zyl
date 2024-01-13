package com.zyl.something.cache.cache;

import com.zyl.something.cache.constant.CacheConstant;
import com.zyl.utils.ApplicationContextUtils;
import com.zyl.utils.RedisUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache{

    private static RedisUtils redisUtils = null;

    public String name(){
        return CacheConstant.REDIS_CACHE_NAME;
    }

    public Object get(String key){
        assembleRedisUtil();
        return redisUtils.get(key);
    }

//    @Override
//    public void put(String key, String unless, Object value) {
//        put(key, unless, value, CacheConstant.TTL, TimeUnit.SECONDS);
//    }
//
//    public void put(String key, Object value, long duration){
//        put(key, null, value, duration);
//    }
//    @Override
//    public void put(String key, String unless, Object value, long duration) {
//        put(key, unless, value, duration, TimeUnit.SECONDS);
//    }
//    @Override
//    public void put(String key, Object value, long duration, TimeUnit timeUnit) {
//       put(key, null, value, duration, timeUnit);
//    }

    public void put(String key, String unless, Object value, long duration, TimeUnit timeUnit){
        assembleRedisUtil();
        if(Objects.isNull(unless)){
            return;
        }else if(Objects.equals(unless, CacheConstant.UNLESS) && Objects.isNull(value)){
            return;
        }
        redisUtils.set(key, value, duration, timeUnit);
    }


    public void put(String key, Object value){
        assembleRedisUtil();
        redisUtils.set(key, value);
    }

    public void delete(String key){
        assembleRedisUtil();
        redisUtils.deleteKey(key);
    }

    private void assembleRedisUtil(){
        if(Objects.isNull(redisUtils)){
            synchronized (this){
                if(Objects.isNull(redisUtils)){
                    redisUtils = ApplicationContextUtils.findBean(RedisUtils.class);
                }
            }
        }
    }
}
