package com.zyl.aop;

import com.alibaba.fastjson.JSONObject;
import com.zyl.common.exception.BusinessException;
import com.zyl.utils.IpUtils;
import com.zyl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/*
方法限流，可丰富，使其支持限流的类型和限流时间单位
 */
@Aspect
@Slf4j
@Component
public class LimiterAspect {

    @Resource
    private RedisUtils redisUtils;

    @Pointcut("@annotation(com.zyl.aop.Limiter)")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint point){
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        log.info("{} 方法开始判断限流",  String.format(" %s.%s", method.getDeclaringClass(), method.getName()));
        Limiter annotation = AnnotationUtils.getAnnotation(method, Limiter.class);
        if(Objects.isNull(annotation)){
            return;
        }
        String limiterKey = getLimiterKey(point, annotation);
        int currCount = Integer.parseInt(JSONObject.toJSONString(redisUtils.get(limiterKey)));
        if(currCount >= annotation.count()){
            log.info("{} 方法开始限流, 当前已经请求 {} 次", String.format(" %s.%s", method.getDeclaringClass(), method.getName()), currCount);
            throw new BusinessException(annotation.limitMsg());
        }
        redisUtils.incrForLua(limiterKey, annotation.time());
    }

    /*
    获取限流器的Key，目前是使用IP
     */
    private String getLimiterKey(JoinPoint point, Limiter limiter){
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        String methodName = method.getName();
        Class<?> declaringClass = method.getDeclaringClass();
        HttpServletRequest response = getRequest();
        StringBuilder stringBuilder = new StringBuilder(IpUtils.findIP(response));
        stringBuilder.append(declaringClass).append(methodName);
        return stringBuilder.toString();
    }

    // 静态获取
    private HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }
}
