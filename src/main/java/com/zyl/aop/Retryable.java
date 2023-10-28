package com.zyl.aop;

import java.lang.annotation.*;

/**
 * 重试机制的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface Retryable {

    int retryTimes() default 3;

    int retryInterval() default 1;

    Class<? extends Throwable>[] RetryFor() default {};

}
