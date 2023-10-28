package com.zyl.cache.config;

import com.zyl.cache.aop.DoubleCacheAble;
import com.zyl.cache.aop.DoubleCacheInterceptor;
import com.zyl.cache.cache.*;
import com.zyl.cache.constant.CacheConstant;
import com.zyl.cache.listener.KeyExpirationListener;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class CacheAutoConfiguration {

    @Bean
    public RedisCache redisCache() {
        return new RedisCache();
    }

    @Bean
    public PrefixKeyCache assembleCacheManager(RedisCache redisCache){
        CaffeineCache caffeineCache = new CaffeineCache(redisCache, CacheConstant.TTL);
        SerialCache serialCache = new SerialCache(caffeineCache);
        PrefixKeyCache prefixKeyCache = new PrefixKeyCache(serialCache);
        CacheManager.putCache(redisCache);
        CacheManager.putCache(caffeineCache);
        CacheManager.putCache(serialCache);
        CacheManager.putCache(prefixKeyCache);
        return prefixKeyCache;
    }

    @Bean
    public Advisor pointcutAdvisor(PrefixKeyCache prefixKeyCache) {
        DoubleCacheInterceptor aroundAdvice = new DoubleCacheInterceptor(prefixKeyCache);
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, DoubleCacheAble.class);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(aroundAdvice);
        return advisor;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public KeyExpirationListener keyExpirationListener(RedisMessageListenerContainer redisMessageListenerContainer,
                                                       PrefixKeyCache prefixKeyCache) {
        KeyExpirationListener keyExpirationListener = new KeyExpirationListener(redisMessageListenerContainer);
        keyExpirationListener.setAnalysisKeyCache(prefixKeyCache);
        return keyExpirationListener;
    }
}
