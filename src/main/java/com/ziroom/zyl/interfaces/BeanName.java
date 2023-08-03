package com.ziroom.zyl.interfaces;

import java.lang.annotation.*;

/*
用于自定义注入 bean 的名字
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanName {

    String name() default "default";
}
