package com.ziroom.zyl.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@Order(1)
public class DataSourceAspect {

    @Pointcut("@annotation(com.ziroom.zyl.datasource.TargetDataSource) || execution( * com.ziroom.zyl.mybatisGenerate.service..*(..))")
    public void dataSourcePoint() {}

    @Around("dataSourcePoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try {
            // 使用反射获取目标类中指定方法名和参数类型的方法对象
            Method m = classz.getMethod(method, parameterTypes);
            // 设置默认的数据源名称
            String dataSource = DatasourceEnum.ZYL.name();
            // 判断方法是否被@TargetDataSource 注解标记。
            if (m.isAnnotationPresent(TargetDataSource.class)) {
                // 通过getAnnotation()方法获取方法上的@TargetDataSource 注解对象。
                TargetDataSource td = m.getAnnotation(TargetDataSource.class);
                // 获取注解对象中设置的数据源名称
                dataSource = td.value().name();
            }
            // 将获取到的数据源名称设置到当前线程的上下文中
            DynamicDataSourceHolder.setDataSource(dataSource);
            // 继续执行目标方法
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.clear();
        }
    }


}
