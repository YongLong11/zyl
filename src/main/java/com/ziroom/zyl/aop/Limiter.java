package com.ziroom.zyl.aop;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiter {

    /*
    限流的key
     */
    String key() default "";

    /*
    限流的类型，目前是直接使用IP，正常是可以自定义、方法、参数等等
     */
    String type() default "IP";
    /*
    限流时长
     */
    int time() default 10;
    /*
    限流次数
     */
    int count() default 10;
    /*
    限流后的提示
     */
    String limitMsg() default "访问过于频繁，请稍候再试";

}
