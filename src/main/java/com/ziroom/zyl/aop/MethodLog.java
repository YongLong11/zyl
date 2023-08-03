package com.ziroom.zyl.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @description:使用该注解会打印参数，返回值和耗时
 * @author zhangyl31@ziroom.com
 * @date: 2023/5/10 12:44
 * @param: null
 * @return: null
 **/
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLog {
    boolean cost() default false;
    boolean needReturn() default false;
}
