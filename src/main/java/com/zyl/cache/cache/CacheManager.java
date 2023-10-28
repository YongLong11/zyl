package com.zyl.cache.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static final ConcurrentHashMap<String, Cache> CACHE_MAP =
            new ConcurrentHashMap<>();

    public static void deleteCache(String key) {
        CACHE_MAP.values().stream().forEach(cache -> {
            cache.delete(key);
        });
    }

    public static Cache getCache(String key) {
        return CACHE_MAP.get(key);
    }

    public static void putCache(Cache cache) {
        CACHE_MAP.putIfAbsent(cache.name(), cache);
    }
}
