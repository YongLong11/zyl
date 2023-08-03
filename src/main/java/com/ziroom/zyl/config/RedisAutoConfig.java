package com.ziroom.zyl.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

/**
 * @ClassName：RedisAutoConfig
 * @Description：
 * @Author：zhangyl31@ziroom.com
 * @Data：2023/5/9 23:24
 **/
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class RedisAutoConfig {
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 设置值value的序列化方式
        template.setValueSerializer(getValueSerializer());
        template.setHashValueSerializer(getValueSerializer());

        // 设置键key的序列化方式
        template.setKeySerializer(getKeySerializer());
        template.setHashKeySerializer(getKeySerializer());

        template.afterPropertiesSet();
        return template;
    }

    // 缓存管理器，使用 @Cacheable 注解时，指定某个 cacheManager， 里面可以指定过期时间
    @Bean(name = "redisCacheManager")
    @Primary
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        //配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //设置 key 过期时间
                .entryTtl(Duration.ofHours(3))
                //设置key序列化规则
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(getKeySerializer()))
                //设置value序列化规则
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getValueSerializer()))
                .disableCachingNullValues();
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }

    // 将key 和 value序列化的方式抽出来，避免后续加 redisCacheManager 的序列化方式不一致
    private StringRedisSerializer getKeySerializer(){
        return new StringRedisSerializer();
    }

    private FastJsonRedisSerializer<Object> getValueSerializer(){
        return new FastJsonRedisSerializer<>(Object.class);
    }
}
