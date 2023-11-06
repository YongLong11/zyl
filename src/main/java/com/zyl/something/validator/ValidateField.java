package com.zyl.something.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateField {
    /**
     * 参数索引位置
     */
    int index() default -1;

    /**
     * 如果参数是基本数据类型或String ，就不用指定该参数，如果参数是对象，要验证对象里面某个属性，就用该参数指定属性名
     */
    String filedName() default "";

    /**
     * 正则验证
     */
    String regStr() default "";

    /**
     * 是否能为空  ， 为true表示不能为空 ， false表示能够为空
     */
    boolean notNull() default false;

    /**
     * 最大长度 ， 用户验证字符串
     */
    int maxLen() default -1;

    /**
     * 最小长度 ， 用户验证字符串
     */
    int minLen() default -1;

    /**
     * 最大值 ，用于验证数字类型数据
     */
    long maxVal() default -1;

    /**
     * 最小值 ，用于验证数值类型数据
     */
    long minVal() default -1;

    /**
     * 用于错误显示的名字
     */
    String showName() default "";

    /**
     * 属性的值
     *
     * @return
     */
    String values() default "";

    /**
     * 分割符
     *
     * @return
     */
    String separator() default "";
}
