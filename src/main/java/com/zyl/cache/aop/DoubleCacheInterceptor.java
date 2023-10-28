package com.zyl.cache.aop;

import com.zyl.cache.cache.Cache;
import com.zyl.cache.cache.PrefixKeyCache;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

@Slf4j
public class DoubleCacheInterceptor implements MethodInterceptor {

    private final Cache cache;

    public DoubleCacheInterceptor(PrefixKeyCache prefixKeyCache) {
        this.cache = prefixKeyCache;
    }

    @Nullable
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        DoubleCacheAble doubleCache = getDoubleCache(method);
        if (doubleCache == null) {
            return invocation.proceed();
        }
        // todo 这部分有问题，需要找到切面方法的返回值类型，包括里面的泛型，经过转换后返回。因为如果是走缓存查到的返回值，类型会转换报错
//        String realKey = String.valueOf(getRealKey(invocation.getArguments(), invocation.getMethod(),
//                doubleCache.key(), doubleCache.value()));
//        Object proceed = cache.get(realKey);
//        Class<?> returnType = method.getReturnType();
//        System.out.println("" + returnType);
//        Object o = returnType.newInstance();
//        if (null == proceed) {
//            proceed = invocation.proceed();
//            cache.put(realKey, doubleCache.unLess(), proceed,
//                    doubleCache.duration(), doubleCache.unit());
//        }
        Object proceed = invocation.proceed();
        return proceed;
    }

    private DoubleCacheAble getDoubleCache(Method method) {
        DoubleCacheAble targetDataSource = method.getAnnotation(DoubleCacheAble.class);
        if (targetDataSource == null) {
            Class<?> declaringClass = method.getDeclaringClass();
            targetDataSource = declaringClass.getAnnotation(DoubleCacheAble.class);
        }
        return targetDataSource;
    }

    public Object getRealKey(Object[] cusArgs, Method method, String key, String value) {
        Object realKey = value + new SpelExpressionParser().parseExpression(key)
                .getValue(new MethodBasedEvaluationContext(null, method, cusArgs, new DefaultParameterNameDiscoverer()));
        log.info("{} SPEL表达式解析得到的完整key: {}", method.getName(), realKey);
        return realKey;
    }

}

