package com.ziroom.zyl.cache.listener;

import com.ziroom.zyl.cache.cache.Cache;
import com.ziroom.zyl.cache.cache.CaffeineCache;
import com.ziroom.zyl.cache.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Objects;

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
    }

    public void setAnalysisKeyCache(Cache cache) {
        this.cache = cache;
    }
}
