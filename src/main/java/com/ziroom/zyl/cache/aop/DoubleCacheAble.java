package com.ziroom.zyl.cache.aop;

import com.ziroom.zyl.cache.constant.CacheConstant;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleCacheAble {
    /**
     * 缓存key：静态写死部分
     */
    String value();

    /**
     * 缓存key：动态spel部分
     */
    String key();

    /**
     * 操作类型 应该有存取、存、删 三种类型
     */
    String type() default "";

    /**
     * 是否缓存空值,默认不缓存空操作类型
     */
    String unLess() default "0";

    int duration() default CacheConstant.TTL;

    TimeUnit unit() default TimeUnit.SECONDS;
}
