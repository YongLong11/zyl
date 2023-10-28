package com.zyl.cache.listener;

import com.zyl.cache.cache.Cache;
import com.zyl.cache.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Slf4j
public class KeyExpirationListener extends KeyExpirationEventMessageListener {
    private Cache cache;

    public KeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        if(expiredKey.contains(CacheConstant.PREFIX_KEY)){
            log.info("【{}】key失效，执行删除", expiredKey);
            cache.delete(expiredKey);
        }
        log.info("非本地缓存【{}】key失效", expiredKey);
    }

    public void setAnalysisKeyCache(Cache cache) {
        this.cache = cache;
    }
}
