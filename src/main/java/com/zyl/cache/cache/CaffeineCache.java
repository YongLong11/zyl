package com.zyl.cache.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zyl.cache.constant.CacheConstant;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CaffeineCache implements com.zyl.cache.cache.Cache {
    private final Cache<String, Object> cache;

    private com.zyl.cache.cache.Cache redisCache;

    public CaffeineCache(com.zyl.cache.cache.Cache cache, int duration){
        this(cache, CacheConstant.minSize, CacheConstant.maxSize, duration);
    }

    public CaffeineCache(com.zyl.cache.cache.Cache cache, int minSize, int maxSize, int duration){
        this.cache = Caffeine.newBuilder().initialCapacity(minSize).maximumSize(maxSize).build();
        this.redisCache = cache;
    }

    @Override
    public String name(){
        return CacheConstant.CAFFEINECACHE_CACHE_NAME;
    }

    @Override
    public Object get(String key){
        Object ifPresent = cache.getIfPresent(key);
        if(Objects.nonNull(ifPresent)){
            return ifPresent;
        }
        return redisCache.get(key);
    }
//    @Override
//    public void put(String key, Object value){
//        cache.put(key, value);
//        redisCache.put(key, value);
//    }
//
//    @Override
//    public void put(String key, String unless, Object value){
//        this.put(key, null, value, CacheConstant.TTL);
//    }
//    @Override
//    public void put(String key, String unless, Object value, long duration){
//        this.put(key, unless, value, duration, TimeUnit.SECONDS);
//    }
//    @Override
//    public void put(String key,  Object value, long duration, TimeUnit timeUnit){
//        this.put(key, null, value, duration, timeUnit);
//    }
//    @Override
//    public void put(String key,  Object value, long duration){
//        put(key, null, value, duration, TimeUnit.SECONDS);
//    }
    @Override
    public void put(String key, String unless, Object value, long duration, TimeUnit timeUnit){
        if(Objects.isNull(unless)){
            return;
        }else if(Objects.equals(unless, CacheConstant.UNLESS) && Objects.isNull(value)){
            return;
        }
        cache.policy().expireVariably().ifPresent(
                e -> e.put(key, value, duration, timeUnit));
        redisCache.put(key, unless, value , duration, timeUnit);
    }
    @Override
    public void delete(String key){
        cache.invalidate(key);
        redisCache.delete(key);
    }

}
