package com.zyl.something.cache.constant;

public interface CacheConstant {
    int maxSize = 4000000;
    int minSize = 128;
    int TTL= 600;

    String REDIS_CACHE_NAME = "RedisCache";
    String CAFFEINECACHE_CACHE_NAME = "CaffeineCache";
    String PREFIX_CACHE_NAME = "prefixCache";
    String SERIAL_CACHE_NAME = "serialCache";

    String PREFIX_KEY = "caffeine:";

    String UNLESS = "0";
}
