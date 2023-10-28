package com.zyl.aop;

import com.zyl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 重试机制的切面
 * RetryFor 暂未使用
 */
@Aspect
@Component
@Slf4j
public class RetryableAspect {

    @Pointcut("@annotation(com.zyl.aop.Retryable)")
    public void RetryablePointCut() {}

    /**
     * 未对处理判断指定的异常
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("RetryablePointCut()")
    public Object advice(ProceedingJoinPoint point) throws Throwable{
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Retryable retryable = AnnotationUtils.getAnnotation(method, Retryable.class);
        if (Objects.isNull(retryable)) {
            return point.proceed();
        }
        int retryTimes =retryable.retryTimes();
        int interval = retryable.retryInterval();

        for (int i = 1; i <= retryTimes; i++) {
            try {
                return point.proceed();
            }catch (Throwable throwable){
                log.error("{}.{} 执行第{}次重试", method.getDeclaringClass(), method.getName(), i);
            }finally {
                Thread.sleep((interval * 1000L));
            }
        }
        throw new BusinessException(String.format("%s.%s 重试次数用光", method.getDeclaringClass(), method.getName()));
    }
}

