package com.ziroom.zyl.utils;

import com.ziroom.zyl.common.constants.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName：RedisUtils
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/4 20:31
 **/
@Component
@Slf4j
public class RedisUtils {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    CacheManager cacheManager;

    public Boolean setFromCacheManager(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (Objects.nonNull(cache)) {
            cache.put(key, value);
            return true;
        }
        return false;
    }

    public Object getFromCacheManager(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (Objects.nonNull(cache) && Objects.nonNull(cache.get(key))) {
                return cache.get(key).get();
        }
        return null;
    }

    public <T> T getFromCacheManager(String cacheName, String key, Class<T> clazz) {
        Cache cache = cacheManager.getCache(cacheName);
        if (Objects.nonNull(cache)) {
            return cache.get(key, clazz);
        }
        return null;
    }


    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置自定义的过期时间（秒）
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void setLittleSeconds(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置x小时的过期时间
     */
    public void setFewHour(String key, Object value, long hours) {
        redisTemplate.opsForValue().set(key, value, hours, TimeUnit.HOURS);
    }

    /**
     * 原子计数
     *
     * @param key     计数的Key
     * @param timeout Key的过期时间，单位【秒】
     */
    public int incrForLua(final String key, final int timeout) {
        return this.luaExec(RedisConstants.INCR_LUA, Integer.class, key, timeout);
    }


    /**
     * 添加元素至Set中，带过期时间
     *
     * @param key     Key值
     * @param timeout 过期时间 单位：秒
     * @param setItem Set的元素集合
     */
    public boolean setAddForLua(final String key, final long timeout, final Object setItem) {
        Long l = this.luaExec(RedisConstants.SET_ADD_LUA, Long.class, key, setItem, timeout);
        return l == null;
    }

    /**
     * 执行LUA脚本
     *
     * @param lua  LUA脚本
     * @param c    返回值类型
     * @param key  Redis的Key值
     * @param args 参数
     */
    public  <T> T luaExec(final String lua, Class<T> c, final String key, Object... args) {

        log.info("RedisUtil执行LUA脚本:\n{}", lua);
        log.info("LUA脚本的Key [{}] 参数列表 [{}]", key, Arrays.asList(args));

        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptText(lua);
        redisScript.setResultType(c);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), args);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
